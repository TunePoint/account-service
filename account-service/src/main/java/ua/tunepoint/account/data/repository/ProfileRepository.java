package ua.tunepoint.account.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.tunepoint.account.data.entity.Profile;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("SELECT p FROM Profile p LEFT JOIN FETCH p.user WHERE p.id = :id")
    Optional<Profile> findByIdWithUser(Long id);
}
