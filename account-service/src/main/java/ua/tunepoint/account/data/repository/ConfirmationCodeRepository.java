package ua.tunepoint.account.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tunepoint.account.data.entity.ConfirmationCode;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Long> {
}
