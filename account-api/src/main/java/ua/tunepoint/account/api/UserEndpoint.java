package ua.tunepoint.account.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.tunepoint.account.model.response.payload.UserPublicPayload;

public interface UserEndpoint {

    @GetMapping("/accounts/users/{id}")
    ResponseEntity<UserPublicPayload> findUser(@PathVariable Long id);
}
