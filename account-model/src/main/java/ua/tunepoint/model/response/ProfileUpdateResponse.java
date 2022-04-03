package ua.tunepoint.model.response;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.tunepoint.model.response.payload.ProfilePayload;
import ua.tunepoint.web.model.CommonResponse;

@SuperBuilder
@NoArgsConstructor
public class ProfileUpdateResponse extends CommonResponse<ProfilePayload> {
}
