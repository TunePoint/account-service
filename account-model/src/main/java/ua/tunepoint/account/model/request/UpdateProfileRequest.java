package ua.tunepoint.account.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {

    private String firstName;

    private String lastName;

    private String pseudonym;

    private String bio;

    private LocalDate birthDate;

    private String avatarId;

    private String city;

    private String country;
}
