package ua.tunepoint.account.model.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ua.tunepoint.account.model.event.profile.ProfileUpdatedEvent;
import ua.tunepoint.event.model.DomainEventType;

@Getter
@RequiredArgsConstructor
public enum ProfileEventType implements DomainEventType {

    PROFILE_UPDATED("updated", ProfileUpdatedEvent.class);

    private final String name;
    private final Class<?> type;
}
