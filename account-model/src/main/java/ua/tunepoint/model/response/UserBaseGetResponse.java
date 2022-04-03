package ua.tunepoint.model.response;


import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.tunepoint.model.response.payload.UserBasePayload;
import ua.tunepoint.web.model.CommonResponse;

@SuperBuilder
@NoArgsConstructor
public class UserBaseGetResponse extends CommonResponse<UserBasePayload> {
}
