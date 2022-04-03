package ua.tunepoint.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.tunepoint.model.response.ProfileGetResponse;

public interface ProfileEndpoint {

    @GetMapping("/profiles/{id}")
    ResponseEntity<ProfileGetResponse> getProfile(@PathVariable("id") Long id);
}
