package ua.tunepoint.account.data.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.tunepoint.account.data.entity.User;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.profile WHERE u.id IN :ids")
    List<User> findBulk(List<Long> ids);
}
