package ua.tunepoint.account.service.support;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.tunepoint.account.data.entity.Profile;
import ua.tunepoint.account.data.mapper.ProfileMapper;
import ua.tunepoint.account.model.response.domain.Resource;
import ua.tunepoint.account.model.response.payload.ProfilePayload;
import ua.tunepoint.account.service.ResourceService;

@Component
@RequiredArgsConstructor
public class ProfileSmartMapper {

    private final ProfileMapper profileMapper;
    private final ResourceService resourceService;

    public ProfilePayload toPayload(Profile profile) {
        Resource avatar = profile.getAvatarId() == null ? null :
            resourceService.getImage(profile.getAvatarId()).orElse(null);

        return profileMapper.toPayload(profile, avatar);
    }
}
