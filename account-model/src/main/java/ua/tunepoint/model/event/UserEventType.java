package ua.tunepoint.model.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ua.tunepoint.event.model.DomainEventType;
import ua.tunepoint.model.event.user.UserCreatedEvent;

@Getter
@RequiredArgsConstructor
public enum UserEventType implements DomainEventType {

    USER_CREATED("created", UserCreatedEvent.class);

    private final String name;
    private final Class<?> type;
}
