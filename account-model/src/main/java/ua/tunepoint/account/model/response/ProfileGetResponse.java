package ua.tunepoint.account.model.response;


import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.tunepoint.account.model.response.payload.ProfilePayload;
import ua.tunepoint.web.model.CommonResponse;

@SuperBuilder
@NoArgsConstructor
public class ProfileGetResponse extends CommonResponse<ProfilePayload> {
}
