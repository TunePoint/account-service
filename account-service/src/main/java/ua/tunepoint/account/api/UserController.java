package ua.tunepoint.account.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tunepoint.account.service.UserService;
import ua.tunepoint.model.response.UserBaseGetResponse;
import ua.tunepoint.model.response.UserFullGetResponse;
import ua.tunepoint.security.UserPrincipal;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserBaseGetResponse> getUserById(@PathVariable Long id) {
        var payload = userService.findUserBase(id);
        var response = UserBaseGetResponse.builder()
                .payload(payload)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserFullGetResponse> getAuthenticatedUser(@AuthenticationPrincipal UserPrincipal currentUser) {
        var payload = userService.findUserFull(currentUser.getId());
        var response = UserFullGetResponse.builder()
                .payload(payload)
                .build();

        return ResponseEntity.ok(response);
    }
}
