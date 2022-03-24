package ua.tunepoint.account.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tunepoint.account.data.entity.VerificationCode;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
}
