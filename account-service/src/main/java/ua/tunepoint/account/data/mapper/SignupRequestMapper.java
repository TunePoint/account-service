package ua.tunepoint.account.data.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.tunepoint.account.data.entity.User;
import ua.tunepoint.model.request.SignupRequest;

@Mapper(componentModel = "spring")
public abstract class SignupRequestMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mappings({
            @Mapping(target = "passwordHash", source = "password", qualifiedByName = "hashPassword")
    })
    public abstract User toUser(SignupRequest request);

    @Named("hashPassword")
    protected String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
