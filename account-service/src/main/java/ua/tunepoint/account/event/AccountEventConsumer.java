package ua.tunepoint.account.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.tunepoint.account.service.ProfileService;
import ua.tunepoint.account.service.StatisticsService;
import ua.tunepoint.account.service.UserService;
import ua.tunepoint.audio.model.event.audio.AudioCreatedEvent;
import ua.tunepoint.audio.model.event.audio.AudioDeletedEvent;
import ua.tunepoint.auth.model.event.user.UserRegisteredEvent;
import ua.tunepoint.event.starter.handler.DomainEventHandlers;
import ua.tunepoint.event.starter.handler.DomainEventHandlersBuilder;
import ua.tunepoint.event.starter.registry.DomainRegistry;

import static ua.tunepoint.audio.model.event.Domain.AUDIO;
import static ua.tunepoint.auth.model.event.AuthDomain.AUTH;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountEventConsumer {

    private final UserService userService;
    private final StatisticsService statisticsService;
    private final DomainRegistry domainRegistry;

    public DomainEventHandlers eventHandlers() {
        return DomainEventHandlersBuilder.withRegistry(domainRegistry)
                .forDomain(AUTH.getName())
                    .onEvent(UserRegisteredEvent.class, this::handleUserCreated)
                .forDomain(AUDIO.getName())
                    .onEvent(AudioCreatedEvent.class, this::handleAudioCreated)
                    .onEvent(AudioDeletedEvent.class, this::handleAudioDeleted)
                .build();
    }

    public void handleUserCreated(UserRegisteredEvent event) {
        log.info("Processing event: {}", event);

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

    public void handleAudioCreated(AudioCreatedEvent event) {
        log.info("Processing event: {}", event);

        statisticsService.incrementAudioCount(event.getAudioOwnerId());
    }

    public void handleAudioDeleted(AudioDeletedEvent event) {
        log.info("Processing event: {}", event);

        statisticsService.decrementAudioCount(event.getAudioOwnerId());
    }
}
