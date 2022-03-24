package ua.tunepoint.account.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.tunepoint.account.data.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
