package ua.tunepoint.account.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.tunepoint.account.service.ProfileService;
import ua.tunepoint.event.starter.handler.DomainEventHandlers;
import ua.tunepoint.event.starter.handler.DomainEventHandlersBuilder;
import ua.tunepoint.event.starter.registry.DomainRegistry;
import ua.tunepoint.auth.model.event.user.UserCreatedEvent;

import static ua.tunepoint.auth.model.event.AuthDomain.USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountEventConsumer {

    private final ProfileService profileService;
    private final DomainRegistry domainRegistry;

    public DomainEventHandlers eventHandlers() {
        return DomainEventHandlersBuilder.withRegistry(domainRegistry)
                .forDomain(USER.getName())
                .onEvent(UserCreatedEvent.class, this::handleUserCreated)
                .build();
    }

    public void handleUserCreated(UserCreatedEvent event) {
        log.info("Handling user created event: " + event);

        try {
            profileService.create(
                    event.getUserId(),
                    event.getUsername(),
                    event.getEmail()
            );
        } catch (Exception ex) {
            log.error("Error occurred while handling event", ex);
        }
    }
}
