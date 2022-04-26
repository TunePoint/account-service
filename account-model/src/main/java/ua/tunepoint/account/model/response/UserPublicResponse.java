package ua.tunepoint.account.model.response;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.tunepoint.account.model.response.payload.UserPublicPayload;
import ua.tunepoint.web.model.CommonResponse;

@SuperBuilder
@NoArgsConstructor
public class UserPublicResponse extends CommonResponse<UserPublicPayload> {
}
