package ua.tunepoint.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.tunepoint.account.data.entity.Profile;
import ua.tunepoint.account.data.mapper.ProfileMapper;
import ua.tunepoint.account.data.repository.ProfileRepository;
import ua.tunepoint.account.model.event.profile.ProfileUpdatedEvent;
import ua.tunepoint.account.model.request.UpdateProfileRequest;
import ua.tunepoint.account.model.response.domain.Resource;
import ua.tunepoint.account.model.response.payload.ProfilePayload;
import ua.tunepoint.account.security.profile.ProfileUpdateAccessManager;
import ua.tunepoint.event.starter.publisher.EventPublisher;
import ua.tunepoint.security.UserPrincipal;
import ua.tunepoint.web.exception.BadRequestException;
import ua.tunepoint.web.exception.NotFoundException;

import javax.annotation.Nullable;

import static java.util.Collections.singletonList;
import static ua.tunepoint.account.model.event.AccountDomain.PROFILE;

@Service
@RequiredArgsConstructor
public class MutateProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileUpdateAccessManager profileUpdateAccessManager;
    private final ResourceService resourceService;
    private final EventPublisher eventPublisher;
    private final ProfileMapper profileMapper;

    public ProfilePayload update(Long id, UpdateProfileRequest request, UserPrincipal user) {
        var profile = profileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profile for user with id '" + id + "' was not found"));

        profileUpdateAccessManager.authorize(user, profile);

        var avatar = request.getAvatarId() != null ? requireAvatar(request.getAvatarId()) : null;

        profileMapper.mergeProfile(profile, request);
        var savedProfile = profileRepository.save(profile);

        var payload = profileMapper.toPayload(savedProfile, avatar);

        publishUpdatedEvent(savedProfile);

        return payload;
    }

    private void publishUpdatedEvent(Profile profile) {
        eventPublisher.publish(
                PROFILE.getName(),
                singletonList(
                        new ProfileUpdatedEvent(
                                profile.getId(),
                                profile.getFirstName(),
                                profile.getLastName(),
                                profile.getPseudonym(),
                                profile.getBio()
                        )
                )
        );
    }

    @Nullable
    private Resource requireAvatar(String id) {
        return resourceService.getImage(id)
                .orElseThrow(() -> new BadRequestException("Image with id " + id + " doesn't exist"));
    }
}
