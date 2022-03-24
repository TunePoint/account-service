package ua.tunepoint.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.tunepoint.account.data.mapper.ProfileMapper;
import ua.tunepoint.account.data.repository.ProfileRepository;
import ua.tunepoint.account.exception.NotFoundException;
import ua.tunepoint.model.request.UpdateProfileRequest;
import ua.tunepoint.model.response.payload.ProfilePayload;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ResourceService resourceService;

    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;

    public ProfilePayload findById(Long id) {
        var profile = profileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profile for user with id '" + id + "' was not found"));

        var avatar = resourceService.getMedia(profile.getAvatarId());

        return profileMapper.toPayload(profile, avatar);
    }

    public ProfilePayload update(Long id, UpdateProfileRequest request) {
        var profile = profileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profile for user with id '" + id + "' was not found"));

        profileMapper.mergeProfile(profile, request);
        var savedProfile = profileRepository.save(profile);

        return profileMapper.toPayload(savedProfile, resourceService.getMedia(savedProfile.getAvatarId()));
    }
}
