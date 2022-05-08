package ua.tunepoint.account.data.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.tunepoint.account.data.entity.UserStatistics;

public interface UserStatisticsRepository extends PagingAndSortingRepository<UserStatistics, Long> {

    @Modifying
    @Query("UPDATE UserStatistics u SET u.audioCount = u.audioCount + 1 WHERE u.id = :id")
    void incrementAudioCount(Long id);

    @Modifying
    @Query("UPDATE UserStatistics u SET u.audioCount = u.audioCount - 1 WHERE u.id = :id")
    void decrementAudioCount(Long id);
}
