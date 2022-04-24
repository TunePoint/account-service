package ua.tunepoint.account.data.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import ua.tunepoint.account.data.entity.Profile;
import ua.tunepoint.model.request.UpdateProfileRequest;
import ua.tunepoint.model.response.domain.Resource;
import ua.tunepoint.model.response.payload.ProfilePayload;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper {

    @Mappings({
            @Mapping(target = "id", source = "profile.id"),
            @Mapping(target = "firstName", source = "profile.firstName"),
            @Mapping(target = "lastName", source = "profile.lastName"),
            @Mapping(target = "bio", source = "profile.bio"),
            @Mapping(target = "birthDate", source = "profile.birthDate"),
            @Mapping(target = "username", source = "profile.username"),
            @Mapping(target = "avatar", source = "avatar")
    })
    ProfilePayload toPayload(Profile profile, Resource avatar);

    void mergeProfile(@MappingTarget Profile profile, UpdateProfileRequest request);
}
