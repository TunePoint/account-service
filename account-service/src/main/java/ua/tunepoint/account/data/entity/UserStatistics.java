package ua.tunepoint.account.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users_statistics")
public class UserStatistics {

    @Id
    private Long id;

    @Column(name = "follower_count")
    private Long followerCount;

    @Column(name = "following_count")
    private Long followingCount;

    @Column(name = "audio_count")
    private Long audioCount;
}
