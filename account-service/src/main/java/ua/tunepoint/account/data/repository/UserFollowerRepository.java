package ua.tunepoint.account.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.tunepoint.account.data.entity.User;
import ua.tunepoint.account.data.entity.UserFollower;
import ua.tunepoint.account.data.entity.UserFollowerId;

public interface UserFollowerRepository extends PagingAndSortingRepository<UserFollower, UserFollowerId> {

    @Query("SELECT uf FROM UserFollower uf JOIN uf.follower WHERE uf.id.userId = :userId")
    Page<UserFollower> findFollowers(Long userId, Pageable pageable);

    @Query("SELECT uf FROM UserFollower uf JOIN uf.user WHERE uf.id.followerId = :userId")
    Page<UserFollower> findWhoFollows(Long userId, Pageable pageable);
}
