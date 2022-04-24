package ua.tunepoint.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tunepoint.account.data.Roles;
import ua.tunepoint.account.data.entity.Role;
import ua.tunepoint.account.data.entity.User;
import ua.tunepoint.account.data.mapper.SignupRequestMapper;
import ua.tunepoint.account.data.mapper.UserMapper;
import ua.tunepoint.account.data.repository.RoleRepository;
import ua.tunepoint.account.data.repository.UserRepository;
import ua.tunepoint.account.security.JwtTokenProvider;
import ua.tunepoint.model.request.AuthenticationRequest;
import ua.tunepoint.model.request.SignupRequest;
import ua.tunepoint.model.request.UpdatePasswordRequest;
import ua.tunepoint.model.response.payload.AuthenticationPayload;
import ua.tunepoint.model.response.payload.RefreshPayload;
import ua.tunepoint.model.response.payload.SignupPayload;
import ua.tunepoint.web.exception.BadRequestException;
import ua.tunepoint.web.exception.ForbiddenException;
import ua.tunepoint.web.exception.NotFoundException;
import ua.tunepoint.web.exception.ServerException;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenProvider tokenProvider;

    private final ConfirmationService confirmationService;

    private final SignupRequestMapper signupRequestMapper;
    private final UserMapper userMapper;

    public AuthenticationPayload authenticate(@NotNull AuthenticationRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User with email '" + request.getEmail() + "' was not found"));

        requireUserConfirmed(user);
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

    @Transactional
    public SignupPayload signup(@NotNull SignupRequest request) {

        requireUniqueCredentials(request);

        var user = signupUser(request);

        confirmationService.sendEmail(user.getEmail(), user.getUsername(), user.getConfirmationCode().getCode());

        return userMapper.toSignupPayload(user);
    }

    @Transactional
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

    private void requireUserConfirmed(@NotNull User user) {
        if (user.getIsConfirmed() == null || !user.getIsConfirmed()) {
            throw new ForbiddenException("User " + user.getUsername() + " is not verified");
        }
    }

    private void requireUniqueCredentials(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("User with specified email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("User with specified username already exists");
        }
    }

    private User signupUser(SignupRequest request) {
        Role role = roleRepository.findById(Roles.ROLE_USER.id())
                .orElseThrow(() -> new ServerException("Unable to assign role to user"));

        User user = signupRequestMapper.toUser(request, Set.of(role));
        user = userRepository.save(user);

        user.setConfirmationCode(confirmationService.generateConfirmationCode(user.getId()));
        return userRepository.save(user);
    }
}
