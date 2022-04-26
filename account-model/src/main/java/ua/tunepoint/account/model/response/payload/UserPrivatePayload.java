package ua.tunepoint.account.model.response.payload;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserPrivatePayload extends BaseUserPayload {

    private String email;
}
