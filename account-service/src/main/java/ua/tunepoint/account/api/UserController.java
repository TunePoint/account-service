package ua.tunepoint.account.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tunepoint.account.service.UserService;
import ua.tunepoint.model.request.UpdateUserRequest;
import ua.tunepoint.model.response.UserBaseResponse;
import ua.tunepoint.model.response.UserFullResponse;
import ua.tunepoint.security.UserPrincipal;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserBaseResponse> getUserById(@PathVariable Long id) {
        var payload = userService.findUserBase(id);
        var response = UserBaseResponse.builder()
                .payload(payload)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserFullResponse> getAuthenticatedUser(@AuthenticationPrincipal UserPrincipal currentUser) {
        var payload = userService.findUserFull(currentUser.getId());
        var response = UserFullResponse.builder()
                .payload(payload)
                .build();

        return ResponseEntity.ok(response);
    }
}
