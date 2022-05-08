package ua.tunepoint.account.model.response.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistics {

    private Long followerCount;
    private Long followingCount;
    private Long audioCount;
}
