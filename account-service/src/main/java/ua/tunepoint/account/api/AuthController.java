package ua.tunepoint.account.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tunepoint.account.service.AuthenticationService;
import ua.tunepoint.account.service.ConfirmationService;
import ua.tunepoint.model.request.AuthenticationRequest;
import ua.tunepoint.model.request.EmailConfirmationRequest;
import ua.tunepoint.model.request.EmailResendRequest;
import ua.tunepoint.model.request.SignupRequest;
import ua.tunepoint.model.request.UpdatePasswordRequest;
import ua.tunepoint.model.response.AuthenticationResponse;
import ua.tunepoint.model.response.RefreshResponse;
import ua.tunepoint.model.response.SignupResponse;
import ua.tunepoint.security.UserPrincipal;
import ua.tunepoint.web.model.StatusResponse;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final ConfirmationService confirmationService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Valid SignupRequest request) {
        var signupPayload = authenticationService.signup(request);
        var response = SignupResponse.builder().payload(signupPayload).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/email/confirm")
    public ResponseEntity<StatusResponse> emailConfirm(@RequestBody @Valid EmailConfirmationRequest request) {
        confirmationService.confirmEmail(request.getEmail(), request.getCode(), request.getPassword());
        return ResponseEntity.ok(StatusResponse.builder().build());
    }

    @PostMapping("/email/resend")
    public ResponseEntity<StatusResponse> emailResend(@RequestBody @Valid EmailResendRequest request) {
        confirmationService.resendEmail(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(StatusResponse.builder().build());
    }

    @PostMapping("/token")
    public ResponseEntity<AuthenticationResponse> token(@RequestBody @Valid AuthenticationRequest request) {
        var authenticationPayload = authenticationService.authenticate(request);
        var response = AuthenticationResponse.builder().payload(authenticationPayload).build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/token/refresh")
    public ResponseEntity<RefreshResponse> refreshToken(@AuthenticationPrincipal UserPrincipal user) {
        var refreshPayload = authenticationService.refresh(user.getUsername());
        var response = RefreshResponse.builder().payload(refreshPayload).build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/password")
    public ResponseEntity<StatusResponse> updatePassword(@RequestBody UpdatePasswordRequest request, @AuthenticationPrincipal UserPrincipal currentUser) {

        authenticationService.updatePassword(request, currentUser.getId());
        return ResponseEntity.ok(StatusResponse.builder().build());
    }
}
