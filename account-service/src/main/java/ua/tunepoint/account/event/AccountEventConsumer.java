package ua.tunepoint.account.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.tunepoint.account.service.ProfileService;
import ua.tunepoint.account.service.UserService;
import ua.tunepoint.auth.model.event.user.UserRegisteredEvent;
import ua.tunepoint.event.starter.handler.DomainEventHandlers;
import ua.tunepoint.event.starter.handler.DomainEventHandlersBuilder;
import ua.tunepoint.event.starter.registry.DomainRegistry;

import static ua.tunepoint.auth.model.event.AuthDomain.AUTH;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountEventConsumer {

    private final UserService userService;
    private final DomainRegistry domainRegistry;

    public DomainEventHandlers eventHandlers() {
        return DomainEventHandlersBuilder.withRegistry(domainRegistry)
                .forDomain(AUTH.getName())
                .onEvent(UserRegisteredEvent.class, this::handleUserCreated)
                .build();
    }

    public void handleUserCreated(UserRegisteredEvent event) {
        log.info("Handling user created event: " + event);

        try {
            userService.create(
                    event.getUserId(),
                    event.getUsername(),
                    event.getEmail()
            );
        } catch (Exception ex) {
            log.error("Error occurred while handling event", ex);
        }
    }
}
