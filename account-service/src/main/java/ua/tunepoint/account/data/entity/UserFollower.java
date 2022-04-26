package ua.tunepoint.account.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_followers")
public class UserFollower {

    @EmbeddedId
    private UserFollowerId id;

    @Column(name = "following_date")
    private LocalDateTime followingDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "follower_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User follower;

    @PrePersist
    protected void onCreate() {
        followingDate = LocalDateTime.now();
    }
}
