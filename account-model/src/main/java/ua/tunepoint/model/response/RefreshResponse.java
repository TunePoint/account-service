package ua.tunepoint.model.response;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.tunepoint.model.response.payload.RefreshPayload;

@SuperBuilder
@NoArgsConstructor
public class RefreshResponse extends CommonResponse<RefreshPayload> {
}
