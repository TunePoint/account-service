package ua.tunepoint.model.response;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.tunepoint.model.response.payload.SignupPayload;

@SuperBuilder
@NoArgsConstructor
public class SignupResponse extends CommonResponse<SignupPayload> {
}
