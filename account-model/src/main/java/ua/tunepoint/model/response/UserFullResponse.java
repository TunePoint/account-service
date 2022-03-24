package ua.tunepoint.model.response;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.tunepoint.model.response.payload.UserFullPayload;

@SuperBuilder
@NoArgsConstructor
public class UserFullResponse extends CommonResponse<UserFullPayload> {
}
