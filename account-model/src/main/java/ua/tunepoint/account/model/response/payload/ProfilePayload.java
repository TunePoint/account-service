package ua.tunepoint.account.model.response.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.tunepoint.account.model.response.domain.Resource;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfilePayload {

    private Long id;

    private String pseudonym;

    private String firstName;

    private String lastName;

    private String bio;

    private LocalDate birthDate;

    private Resource avatar;

    private String country;

    private String city;
}
