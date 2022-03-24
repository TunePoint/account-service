package ua.tunepoint.account.data.mapper;

import org.mapstruct.Mapper;
import ua.tunepoint.account.data.entity.Role;
import ua.tunepoint.account.data.entity.User;
import ua.tunepoint.model.response.payload.SignupPayload;
import ua.tunepoint.model.response.payload.UserBasePayload;
import ua.tunepoint.model.response.payload.UserFullPayload;

@Mapper(componentModel = "spring")
public interface UserMapper {

    SignupPayload toSignupPayload(User user);

    UserBasePayload toUserBase(User user);

    UserFullPayload toUserFull(User user);

    default String mapRole(Role role) {
        return role.getName();
    }
}
