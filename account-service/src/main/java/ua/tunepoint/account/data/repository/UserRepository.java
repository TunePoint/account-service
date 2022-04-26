package ua.tunepoint.account.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.tunepoint.account.data.entity.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
}
