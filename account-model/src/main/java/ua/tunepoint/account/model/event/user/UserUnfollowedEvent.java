package ua.tunepoint.account.model.event.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.tunepoint.event.model.DomainEvent;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserUnfollowedEvent implements DomainEvent {

    private Long userId;
    private Long followerId;
    private LocalDateTime time;
}
