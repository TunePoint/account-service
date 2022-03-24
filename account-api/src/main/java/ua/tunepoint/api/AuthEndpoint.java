package ua.tunepoint.api;

import org.springframework.http.ResponseEntity;
import ua.tunepoint.model.request.AuthenticationRequest;
import ua.tunepoint.model.request.SignupRequest;
import ua.tunepoint.model.request.VerificationRequest;

public interface AuthEndpoint {

    ResponseEntity<?> signup(SignupRequest request);

    ResponseEntity<?> login(AuthenticationRequest request);

    ResponseEntity<?> verify(VerificationRequest request);
}