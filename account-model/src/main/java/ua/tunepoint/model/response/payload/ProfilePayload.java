package ua.tunepoint.model.response.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.tunepoint.model.response.domain.Media;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfilePayload {

    private Long id;

    private String firstName;

    private String lastName;

    private String bio;

    private LocalDate birthDate;

    private Media avatar;
}
