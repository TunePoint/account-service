package ua.tunepoint.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailConfirmationRequest {

    @Email
    private String email;

    @NotBlank
    private String code;

    @NotBlank
    private String password;
}
