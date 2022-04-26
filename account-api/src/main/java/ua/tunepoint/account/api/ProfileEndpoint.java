package ua.tunepoint.account.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.tunepoint.account.model.response.ProfileGetResponse;

public interface ProfileEndpoint {

    @GetMapping("/accounts/profiles/{id}")
    ResponseEntity<ProfileGetResponse> getProfile(@PathVariable("id") Long id);
}
