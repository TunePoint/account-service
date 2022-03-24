package ua.tunepoint.account.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.tunepoint.account.exception.ExternalException;
import ua.tunepoint.account.exception.ForbiddenException;
import ua.tunepoint.account.exception.NotFoundException;
import ua.tunepoint.model.response.StatusResponse;

import static ua.tunepoint.account.utils.ResponseUtils.constructResponse;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StatusResponse> handleNotFound(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(constructResponse(ex, 1));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<StatusResponse> handleForbidden(ForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(constructResponse(ex, 2));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<StatusResponse> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(constructResponse(ex, 3));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StatusResponse> handleOther(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(constructResponse(ex, 4));
    }
}
