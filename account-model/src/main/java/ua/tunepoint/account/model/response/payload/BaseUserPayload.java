package ua.tunepoint.account.model.response.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseUserPayload {

    private Long id;
    private String username;
    private ProfilePayload profile;
}
