package com.enterprise.inbox.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = baseProblem(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                "validation_error",
                request
        );
        Map<String, String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (a, b) -> a));
        problemDetail.setProperty("errors", fieldErrors);
        return problemDetail;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return baseProblem(HttpStatus.BAD_REQUEST, "Malformed request body", "malformed_body", request);
    }

    @ExceptionHandler(DomainException.class)
    public ProblemDetail handleDomainException(DomainException ex, HttpServletRequest request) {
        return baseProblem(ex.getStatus(), ex.getMessage(), ex.getCode(), request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        return baseProblem(HttpStatus.UNAUTHORIZED, "Invalid username or password", "auth_failed", request);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpected(Exception ex, HttpServletRequest request) {
        return baseProblem(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                "internal_error",
                request
        );
    }

    private ProblemDetail baseProblem(HttpStatus status, String detail, String code, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle(status.getReasonPhrase());
        problemDetail.setType(URI.create("https://api.enterprise-inbox.dev/problems/" + code));
        problemDetail.setProperty("code", code);
        problemDetail.setProperty("timestamp", OffsetDateTime.now().toString());
        problemDetail.setProperty("path", request.getRequestURI());
        return problemDetail;
    }
}
