package ua.tunepoint.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tunepoint.account.config.properties.ConfirmationProperties;
import ua.tunepoint.account.data.entity.ConfirmationCode;
import ua.tunepoint.account.data.repository.ConfirmationCodeRepository;
import ua.tunepoint.account.data.repository.UserRepository;
import ua.tunepoint.event.starter.publisher.EventPublisher;
import ua.tunepoint.web.exception.BadRequestException;
import ua.tunepoint.web.exception.ForbiddenException;
import ua.tunepoint.web.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Map;

import static java.util.Collections.singletonList;
import static ua.tunepoint.account.utils.EventUtils.toCreatedEvent;
import static ua.tunepoint.model.event.AccountDomain.USER;

@Service
@RequiredArgsConstructor
public class ConfirmationService {

    private final UserRepository userRepository;
    private final ConfirmationCodeRepository confirmationCodeRepository;

    private final PasswordEncoder passwordEncoder;
    private final EventPublisher eventPublisher;
    private final MailService mailService;
    private final ConfirmationCodeProvider codeProvider;

    private final ConfirmationProperties props;

    @Transactional
    public void confirmEmail(String email, String code, String password) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("user with email " + email + " was not found"));

        requirePasswordMatch(password, user.getPasswordHash(), "wrong password");

        if (user.getIsConfirmed()) {
            throw new BadRequestException("email " + email + " is already verified");
        }

        var confirmationCode = user.getConfirmationCode();

        if (confirmationCode.getAttempts() > props.getAttempts()) {
            throw new BadRequestException("the limit of attempts is exceeded, get new confirmation code");
        }

        if (confirmationCode.getDueDate().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("confirmation code is expired");
        }

        if (confirmationCode.getCode().equals(code)) {
            user.setIsConfirmed(true);
            userRepository.save(user);
        } else {
            confirmationCode.incrementAttempts();
            confirmationCodeRepository.save(confirmationCode);

            throw new BadRequestException("incorrect confirmation code, try another one");
        }

        eventPublisher.publish(USER.getName(),
                singletonList(toCreatedEvent(user))
        );
    }

    @Transactional
    public void resendEmail(String email, String password) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("user with email " + email + " was not found"));

        requirePasswordMatch(password, user.getPasswordHash(), "passwords doesn't match");

        if (user.getIsConfirmed()) {
            throw new BadRequestException("user is already verified");
        }

        var confirmationCode = user.getConfirmationCode();
        if (confirmationCode.getLastSent().isBefore(LocalDateTime.now().minus(props.getCode().getBlockResendPeriod()))) {

            String newCode = codeProvider.generate();

            sendEmail(email, user.getUsername(), newCode);

            confirmationCode.reset(newCode, props.getCode().getDuePeriod());
            confirmationCodeRepository.save(confirmationCode);
        } else {
            throw new BadRequestException("you can receive next email after " + confirmationCode.getLastSent().plus(props.getCode().getBlockResendPeriod()));
        }
    }

    public void sendEmail(String address, String username, String code) {
        mailService.sendEmail(address, props.getEmail().getSubject(), props.getEmail().getTemplate(), Map.of(
                "username", username,
                "code", code
        ));
    }

    public ConfirmationCode generateConfirmationCode(Long id) {
        return ConfirmationCode.create(id, codeProvider.generate(), props.getCode().getDuePeriod());
    }

    private void requirePasswordMatch(String password, String hashedPassword, String errorMessage) {
        if (!passwordEncoder.matches(password, hashedPassword)) {
            throw new ForbiddenException(errorMessage);
        }
    }
}
