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
import ua.tunepoint.account.service.ProfileService;
import ua.tunepoint.model.request.UpdateProfileRequest;
import ua.tunepoint.model.response.ProfileResponse;
import ua.tunepoint.security.UserPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(doGetProfile(id));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProfileResponse> getCurrentUserProfile(@AuthenticationPrincipal UserPrincipal currentUser) {
        return ResponseEntity.ok(doGetProfile(currentUser.getId()));
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProfileResponse> updateProfile(@AuthenticationPrincipal UserPrincipal currentUser, @RequestBody UpdateProfileRequest request) {
        var payload = profileService.update(currentUser.getId(), request);
        var response = ProfileResponse.builder()
                .payload(payload)
                .build();
        return ResponseEntity.ok(response);
    }

    private ProfileResponse doGetProfile(Long id) {
        var payload = profileService.findById(id);
        return ProfileResponse.builder()
                .payload(payload)
                .build();
    }
}
