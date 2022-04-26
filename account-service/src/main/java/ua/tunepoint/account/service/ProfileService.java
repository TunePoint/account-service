package ua.tunepoint.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.tunepoint.account.data.entity.Profile;
import ua.tunepoint.account.data.mapper.ProfileMapper;
import ua.tunepoint.account.data.repository.ProfileRepository;
import ua.tunepoint.account.security.profile.ProfileUpdateAccessManager;
import ua.tunepoint.account.model.request.UpdateProfileRequest;
import ua.tunepoint.account.model.response.domain.Resource;
import ua.tunepoint.account.model.response.payload.ProfilePayload;
import ua.tunepoint.security.UserPrincipal;
import ua.tunepoint.web.exception.BadRequestException;
import ua.tunepoint.web.exception.NotFoundException;

import javax.annotation.Nullable;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ResourceService resourceService;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final ProfileUpdateAccessManager profileUpdateAccessManager;

    public void create(Long id) {
        profileRepository.save(
                Profile.create(id)
        );
    }

    public ProfilePayload findById(Long id) {
        var profile = profileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profile for user with id '" + id + "' was not found"));

        var avatar = resourceService.getImage(profile.getAvatarId())
                .orElse(null);

        return profileMapper.toPayload(profile, avatar);
    }

    public ProfilePayload update(Long id, UpdateProfileRequest request, UserPrincipal user) {
        var profile = profileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profile for user with id '" + id + "' was not found"));

        profileUpdateAccessManager.authorize(user, profile);

        var avatar = request.getAvatarId() != null ? requireAvatar(request.getAvatarId()) : null;

        profileMapper.mergeProfile(profile, request);
        var savedProfile = profileRepository.save(profile);

        return profileMapper.toPayload(savedProfile, avatar);
    }

    @Nullable
    private Resource requireAvatar(String id) {
        return resourceService.getImage(id)
                .orElseThrow(() -> new BadRequestException("Image with id " + id + " doesn't exist"));
    }
}
