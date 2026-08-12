package com.user.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.TooManyRequests;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;



//import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice // Tells Spring: "Send all crashes here!"
public class GlobalExceptionHandler {

    // ────────────────────────────────────────────────────────
    // 1. 400 BAD REQUEST (Validation Errors from DTOs)
    // ────────────────────────────────────────────────────────
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> validationErrors.put(error.getField(), error.getDefaultMessage()));

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation Failed");
        body.put("message", "Invalid data provided");
        body.put("fieldErrors", validationErrors); // Attach the specific field errors here

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleMalformedJson(HttpMessageNotReadableException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Malformed JSON request. Please check your request body.");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid parameter type in the URL: " + ex.getName());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return buildErrorResponse(HttpStatus.METHOD_NOT_ALLOWED,
                "The HTTP method used is not supported for this endpoint.");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Object> handleEndpointNotFound(NoResourceFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "The requested endpoint does not exist.");
    }

    // ────────────────────────────────────────────────────────
    // 2. 404 NOT FOUND (When a DB search comes up empty)
    // ────────────────────────────────────────────────────────
 

    // ────────────────────────────────────────────────────────
    // 3. 409 CONFLICT (Duplicate usernames, emails, etc.)
    // ────────────────────────────────────────────────────────
    // @ExceptionHandler(DataIntegrityViolationException.class)
    // public ResponseEntity<Object> handleDataConflict(DataIntegrityViolationException ex) {
    //     return buildErrorResponse(HttpStatus.CONFLICT, "Database conflict: Duplicate entry or foreign key violation.");
    // }

    // ────────────────────────────────────────────────────────
    // 4. 401 UNAUTHORIZED (Wrong password / Bad JWT)
    // ────────────────────────────────────────────────────────
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid username or password.");
    }

    // ────────────────────────────────────────────────────────
    // 5. 403 FORBIDDEN (Logged in, but not allowed to do this)
    // ────────────────────────────────────────────────────────
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, "You do not have permission to access this resource.");
    }
      @ExceptionHandler(TooManyRequests.class)
    public ResponseEntity<Object> handleToManyRequest(TooManyRequests ex) {
        return buildErrorResponse(HttpStatus.TOO_MANY_REQUESTS, "to many request");
    }

    // ────────────────────────────────────────────────────────
    // 5. 423 FORBIDDEN (Logged in, but not allowed to do this)
    // ────────────────────────────────────────────────────────
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<Object> handleBanned(LockedException ex) {
        return buildErrorResponse(HttpStatus.LOCKED, "you are banned");
    }
    // Method 2 (You probably have something like this right next to it)

    // ────────────────────────────────────────────────────────
    // 6. 400 BAD REQUEST (For your custom RuntimeExceptions)
    // ────────────────────────────────────────────────────────
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException ex) {
        return buildErrorResponse(ex.getStatus(), ex.getMessage());
    }

    // ────────────────────────────────────────────────────────
    // 7. 500 INTERNAL SERVER ERROR (The Catch-All for unknown bugs)
    // ────────────────────────────────────────────────────────
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllOtherExceptions(Exception ex) {
        // THIS LINE WILL SAVE YOUR LIFE DURING DEBUGGING!
        ex.printStackTrace();
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred on the server.");
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<Object> handleDisabledAccount(DisabledException ex) {
        return buildErrorResponse(HttpStatus.LOCKED,
                "Your account has been banned or disabled. Please contact support.");
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        return new ResponseEntity<>(body, status);
    }
}
