package ua.tunepoint.account.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ua.tunepoint.account.model.response.UserBulkResponse;
import ua.tunepoint.account.model.response.UserPublicResponse;

import java.util.List;

public interface UserEndpoint {

    @GetMapping("/accounts/users/{id}")
    ResponseEntity<UserPublicResponse> findUser(@PathVariable("id") Long id);

    @GetMapping("/accounts/users/_bulk")
    ResponseEntity<UserBulkResponse> searchBulk(@RequestParam("ids") List<Long> ids);
}
