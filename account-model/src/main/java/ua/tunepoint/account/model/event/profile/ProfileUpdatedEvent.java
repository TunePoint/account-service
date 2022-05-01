package ua.tunepoint.account.model.event.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.tunepoint.event.model.DomainEvent;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdatedEvent implements DomainEvent {

    private Long id;

    private String firstName;

    private String lastName;

    private String pseudonym;

    private String bio;
}
