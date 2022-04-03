package ua.tunepoint.model.response;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.tunepoint.model.response.payload.RefreshPayload;
import ua.tunepoint.web.model.CommonResponse;

@SuperBuilder
@NoArgsConstructor
public class RefreshResponse extends CommonResponse<RefreshPayload> {
}
