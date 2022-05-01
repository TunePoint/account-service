package ua.tunepoint.account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.tunepoint.account.event.AccountEventConsumer;
import ua.tunepoint.account.model.event.ProfileEventType;
import ua.tunepoint.account.model.event.UserEventType;
import ua.tunepoint.auth.model.event.AuthEventType;
import ua.tunepoint.event.starter.DomainRelation;
import ua.tunepoint.event.starter.handler.DomainEventHandlers;
import ua.tunepoint.event.starter.registry.DomainRegistry;
import ua.tunepoint.event.starter.registry.builder.DomainRegistryBuilder;

import java.util.Set;

import static ua.tunepoint.account.model.event.AccountDomain.PROFILE;
import static ua.tunepoint.account.model.event.AccountDomain.USER;
import static ua.tunepoint.auth.model.event.AuthDomain.AUTH;


@Configuration
public class EventConfiguration {

    @Bean
    public DomainRegistry domainRegistry() {
        return new DomainRegistryBuilder()
                .register(
                        AUTH.getName(),
                        AuthEventType.values(),
                        Set.of(DomainRelation.CONSUMER)
                )
                .register(
                        USER.getName(),
                        UserEventType.values(),
                        Set.of(DomainRelation.PRODUCER)
                )
                .register(
                        PROFILE.getName(),
                        ProfileEventType.values(),
                        Set.of(DomainRelation.PRODUCER)
                )
                .build();
    }

    @Bean
    public DomainEventHandlers domainEventHandlers(AccountEventConsumer eventConsumer) {
        return eventConsumer.eventHandlers();
    }
}
