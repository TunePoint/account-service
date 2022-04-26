package ua.tunepoint.account.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UserFollowerId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "follower_id")
    private Long followerId;
}
