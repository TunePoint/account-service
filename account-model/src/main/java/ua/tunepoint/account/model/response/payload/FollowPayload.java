package ua.tunepoint.account.model.response.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowPayload {

    private UserPublicPayload user;
    private LocalDateTime followSince;
}
