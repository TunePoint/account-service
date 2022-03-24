package ua.tunepoint.model.response;


import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.tunepoint.model.response.payload.ProfilePayload;

@SuperBuilder
@NoArgsConstructor
public class ProfileResponse extends CommonResponse<ProfilePayload> {
}
