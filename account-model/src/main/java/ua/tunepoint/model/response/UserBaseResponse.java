package ua.tunepoint.model.response;


import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.tunepoint.model.response.payload.UserBasePayload;

@SuperBuilder
@NoArgsConstructor
public class UserBaseResponse extends CommonResponse<UserBasePayload> {
}
