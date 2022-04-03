package ua.tunepoint.model.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.tunepoint.model.response.payload.AuthenticationPayload;
import ua.tunepoint.web.model.CommonResponse;

@SuperBuilder
@NoArgsConstructor
public class AuthenticationResponse extends CommonResponse<AuthenticationPayload> {

}
