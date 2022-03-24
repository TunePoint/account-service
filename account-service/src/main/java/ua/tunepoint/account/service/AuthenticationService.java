package ua.tunepoint.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.tunepoint.account.data.entity.Role;
import ua.tunepoint.account.data.entity.User;
import ua.tunepoint.account.data.mapper.SignupRequestMapper;
import ua.tunepoint.account.data.mapper.UserMapper;
import ua.tunepoint.account.data.repository.RoleRepository;
import ua.tunepoint.account.data.repository.UserRepository;
import ua.tunepoint.account.exception.ForbiddenException;
import ua.tunepoint.account.exception.InternalException;
import ua.tunepoint.account.exception.NotFoundException;
import ua.tunepoint.account.security.JwtTokenProvider;
import ua.tunepoint.account.security.Roles;
import ua.tunepoint.model.request.AuthenticationRequest;
import ua.tunepoint.model.request.UpdatePasswordRequest;
import ua.tunepoint.model.request.SignupRequest;
import ua.tunepoint.model.response.payload.AuthenticationPayload;
import ua.tunepoint.model.response.payload.RefreshPayload;
import ua.tunepoint.model.response.payload.SignupPayload;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenProvider tokenProvider;

    private final SignupRequestMapper signupRequestMapper;
    private final UserMapper userMapper;

    public AuthenticationPayload authenticate(@NotNull AuthenticationRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User with email '" + request.getEmail() + "' was not found"));

        requirePasswordMatch(request.getPassword(), user.getPasswordHash(), "Wrong password");

        return AuthenticationPayload.builder()
                .accessToken(tokenProvider.accessToken(user))
                .refreshToken(tokenProvider.refreshToken(user))
                .build();
    }

    public RefreshPayload refresh(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User '" + username + "' was not found"));

        return RefreshPayload.builder()
                .accessToken(tokenProvider.accessToken(user))
                .build();
    }

    public SignupPayload signup(@NotNull SignupRequest request) {

        var user = signupUser(request);

        var savedUser = userRepository.save(user);

        return userMapper.toSignupPayload(savedUser);
    }

    public void updatePassword(@NotNull UpdatePasswordRequest request, Long userId) {

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " was not found"));

        requirePasswordMatch(request.getOldPassword(), user.getPasswordHash(), "Old password is invalid");

        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }

    private void requirePasswordMatch(String password, String hashedPassword, String errorMessage) {

        if (!passwordEncoder.matches(password, hashedPassword)) {
            throw new ForbiddenException(errorMessage);
        }
    }

    private User signupUser(SignupRequest request) {

        User newUser = signupRequestMapper.toUser(request);

        Role role = roleRepository.findByName(Roles.ROLE_USER.name())
                .orElseThrow(() -> new InternalException("Unable to assign role to user"));

        newUser.setRoles(Set.of(role));
        return newUser;
    }
}
