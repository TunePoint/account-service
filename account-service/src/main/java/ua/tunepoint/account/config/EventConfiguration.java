package ua.tunepoint.account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.tunepoint.event.starter.DomainRelation;
import ua.tunepoint.event.starter.registry.DomainRegistry;
import ua.tunepoint.event.starter.registry.builder.DomainRegistryBuilder;
import ua.tunepoint.model.event.UserEventType;

import java.util.Set;

import static ua.tunepoint.model.event.AccountDomain.USER;

@Configuration
public class EventConfiguration {

    @Bean
    public DomainRegistry domainRegistry() {
        return new DomainRegistryBuilder()
                .register(USER.getName(), UserEventType.values(), Set.of(DomainRelation.PRODUCER))
                .build();
    }
}
