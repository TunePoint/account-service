package ua.tunepoint.account.model.response;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;
import ua.tunepoint.account.model.response.payload.FollowPayload;
import ua.tunepoint.web.model.CommonResponse;

@NoArgsConstructor
@SuperBuilder
public class ListFollowersResponse extends CommonResponse<Page<FollowPayload>> {
}
