package ua.tunepoint.model.request;

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

    private String bio;

    private LocalDate birthDate;

    private String avatarId;
}
