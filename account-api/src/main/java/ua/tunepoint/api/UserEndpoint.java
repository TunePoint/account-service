package ua.tunepoint.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.tunepoint.model.response.UserBaseGetResponse;

public interface UserEndpoint {

    @GetMapping("/users/{id}")
    ResponseEntity<UserBaseGetResponse> getUser(@PathVariable("id") Long id);
}
