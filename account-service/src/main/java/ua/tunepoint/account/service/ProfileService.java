package ua.tunepoint.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.tunepoint.account.data.entity.Profile;
import ua.tunepoint.account.data.mapper.ProfileMapper;
import ua.tunepoint.account.data.repository.ProfileRepository;
import ua.tunepoint.account.model.event.profile.ProfileUpdatedEvent;
import ua.tunepoint.account.security.profile.ProfileUpdateAccessManager;
import ua.tunepoint.account.model.request.UpdateProfileRequest;
import ua.tunepoint.account.model.response.domain.Resource;
import ua.tunepoint.account.model.response.payload.ProfilePayload;
import ua.tunepoint.event.starter.publisher.EventPublisher;
import ua.tunepoint.security.UserPrincipal;
import ua.tunepoint.web.exception.BadRequestException;
import ua.tunepoint.web.exception.NotFoundException;

import javax.annotation.Nullable;

import static java.util.Collections.singletonList;
import static ua.tunepoint.account.model.event.AccountDomain.PROFILE;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ResourceService resourceService;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public ProfilePayload findById(Long id) {
        var profile = profileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profile for user with id '" + id + "' was not found"));

        var avatar = resourceService.getImage(profile.getAvatarId())
                .orElse(null);

        return profileMapper.toPayload(profile, avatar);
    }
}
