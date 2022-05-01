package ua.tunepoint.account.model.response.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Statistics {

    private Long followerCount;
    private Long followingCount;
}
