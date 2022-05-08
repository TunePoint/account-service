package ua.tunepoint.account.data.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ua.tunepoint.account.data.entity.User;
import ua.tunepoint.account.model.response.payload.UserPrivatePayload;
import ua.tunepoint.account.model.response.payload.ProfilePayload;
import ua.tunepoint.account.model.response.payload.UserPublicPayload;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", source = "user.id"),
            @Mapping(target = "username", source = "user.username"),
            @Mapping(target = "profile", source = "profile"),
            @Mapping(target = "statistics", source = "user.statistics"),
            @Mapping(target = "isFollowed", source = "isFollowed")
    })
    UserPublicPayload toPublicUser(User user, ProfilePayload profile, Boolean isFollowed);

    @Mappings({
            @Mapping(target = "id", source = "user.id"),
            @Mapping(target = "username", source = "user.username"),
            @Mapping(target = "email", source = "user.email"),
            @Mapping(target = "profile", source = "profile"),
            @Mapping(target = "statistics", source = "user.statistics")
    })
    UserPrivatePayload toPrivateUser(User user, ProfilePayload profile);
}
