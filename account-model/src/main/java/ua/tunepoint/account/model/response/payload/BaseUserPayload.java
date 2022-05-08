package ua.tunepoint.account.model.response.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.tunepoint.account.model.response.domain.Statistics;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseUserPayload {

    private Long id;
    private String username;
    private Statistics statistics;
    private ProfilePayload profile;

    /**
     * Is followed by current user (always false for unauthorized requests)
     */
    private Boolean isFollowed;
}
