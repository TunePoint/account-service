package ua.tunepoint.account.model.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ua.tunepoint.account.model.event.user.UserFollowedEvent;
import ua.tunepoint.account.model.event.user.UserUnfollowedEvent;
import ua.tunepoint.event.model.DomainEventType;

@Getter
@RequiredArgsConstructor
public enum UserEventType implements DomainEventType {

    USER_FOLLOWED("followed", UserFollowedEvent.class),
    USER_UNFOLLOWED("unfollowed", UserUnfollowedEvent.class);

    private final String name;
    private final Class<?> type;
}
