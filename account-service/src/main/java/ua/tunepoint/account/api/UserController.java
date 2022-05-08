package ua.tunepoint.account.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.tunepoint.account.model.response.ListFollowersResponse;
import ua.tunepoint.account.model.response.UserBulkResponse;
import ua.tunepoint.account.model.response.UserPrivateResponse;
import ua.tunepoint.account.model.response.UserPublicResponse;
import ua.tunepoint.account.service.FollowService;
import ua.tunepoint.account.service.MutateFollowService;
import ua.tunepoint.account.service.UserService;
import ua.tunepoint.security.UserPrincipal;
import ua.tunepoint.web.model.StatusResponse;

import java.util.List;

import static ua.tunepoint.account.utils.UserUtils.extractId;

@RequiredArgsConstructor
@RestController
@RequestMapping("/accounts/users")
public class UserController {

    private final UserService userService;
    private final FollowService followService;
    private final MutateFollowService mutateFollowService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserPrivateResponse> getSelf(@AuthenticationPrincipal UserPrincipal user) {
        var payload = userService.findUserPrivate(user.getId());
        return ResponseEntity.ok(
                UserPrivateResponse.builder()
                        .payload(payload)
                        .build()
        );
    }

    @GetMapping("/_bulk")
    public ResponseEntity<UserBulkResponse> searchBulk(@RequestParam("ids") List<Long> ids, @AuthenticationPrincipal UserPrincipal user) {
        var payload = userService.findBulk(ids, extractId(user));
        return ResponseEntity.ok(
                UserBulkResponse.builder()
                        .payload(payload)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPublicResponse> getUser(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal user) {
        var payload = userService.findUserPublic(id, extractId(user));
        return ResponseEntity.ok(
                UserPublicResponse.builder()
                        .payload(payload)
                        .build()
        );
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<ListFollowersResponse> listFollowers(@PathVariable Long id, @PageableDefault Pageable pageable, @AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(
                ListFollowersResponse.builder()
                        .payload(
                                followService.findFollowers(id, pageable, extractId(user))
                        )
                        .build()
        );
    }

    @GetMapping("/{id}/following")
    public ResponseEntity<ListFollowersResponse> listWhoFollows(@PathVariable Long id, @PageableDefault Pageable pageable, @AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(
                ListFollowersResponse.builder()
                        .payload(
                                followService.findWhoFollows(id, pageable, extractId(user))
                        )
                        .build()
        );
    }

    @PostMapping("/{id}/followers")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<StatusResponse> follow(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal user) {
        mutateFollowService.follow(id, user.getId());

        return ResponseEntity.ok(
                StatusResponse.builder().build()
        );
    }

    @DeleteMapping("/{id}/followers")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<StatusResponse> unfollow(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal user) {
        mutateFollowService.unfollow(id, user.getId());

        return ResponseEntity.ok(
                StatusResponse.builder().build()
        );
    }
}
