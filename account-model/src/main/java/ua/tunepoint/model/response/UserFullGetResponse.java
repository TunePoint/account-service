package ua.tunepoint.model.response;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.tunepoint.model.response.payload.UserFullPayload;
import ua.tunepoint.web.model.CommonResponse;

@SuperBuilder
@NoArgsConstructor
public class UserFullGetResponse extends CommonResponse<UserFullPayload> {
}
