package com.cdac.globalExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cdac.dto.ApiError;
import com.cdac.exception.custom.AuthenticationException;
import com.cdac.exception.custom.CategoryNotFoundException;
import com.cdac.exception.custom.DuplicateResourceException;
import com.cdac.exception.custom.EventNotFoundException;
import com.cdac.exception.custom.InternalErrorException;
import com.cdac.exception.custom.InvalidActionException;
import com.cdac.exception.custom.InvalidDataException;
import com.cdac.exception.custom.NoDataFoundException;
import com.cdac.exception.custom.RegistrationNotFoundException;
import com.cdac.exception.custom.ResourceNotFoundException;
import com.cdac.exception.custom.UserNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Generic resource not found (base)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                LocalDateTime.now(),
                req.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Specific: Event not found
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ApiError> handleEventNotFound(EventNotFoundException ex, HttpServletRequest req) {
        ApiError err = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Event Not Found",
                ex.getMessage(),
                LocalDateTime.now(),
                req.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    // Specific: Category not found
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiError> handleCategoryNotFound(CategoryNotFoundException ex, HttpServletRequest req) {
        ApiError err = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Category Not Found",
                ex.getMessage(),
                LocalDateTime.now(),
                req.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    // Specific: Registration not found
    @ExceptionHandler(RegistrationNotFoundException.class)
    public ResponseEntity<ApiError> handleRegistrationNotFound(RegistrationNotFoundException ex, HttpServletRequest req) {
        ApiError err = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Registration Not Found",
                ex.getMessage(),
                LocalDateTime.now(),
                req.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    // 404 - User not found
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex, HttpServletRequest req) {
        ApiError err = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "User Not Found",
                ex.getMessage(),
                LocalDateTime.now(),
                req.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    // 404 - No data found (empty lists)
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ApiError> handleNoData(NoDataFoundException ex, HttpServletRequest req) {
        ApiError err = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "No Data Found",
                ex.getMessage(),
                LocalDateTime.now(),
                req.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    // 401 - Authentication failed
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuth(AuthenticationException ex, HttpServletRequest req) {
        ApiError err = new ApiError(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                ex.getMessage(),
                LocalDateTime.now(),
                req.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

    // 409 - Duplicate / conflict
    @ExceptionHandler({ DuplicateResourceException.class, DataIntegrityViolationException.class })
    public ResponseEntity<ApiError> handleConflict(RuntimeException ex, HttpServletRequest req) {
        ApiError err = new ApiError(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                LocalDateTime.now(),
                req.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    // 400 - Invalid data / bad request
    @ExceptionHandler({ InvalidDataException.class, InvalidActionException.class })
    public ResponseEntity<ApiError> handleBadRequest(RuntimeException ex, HttpServletRequest req) {
        ApiError err = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                LocalDateTime.now(),
                req.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    // 500 - Internal server error (explicit)
    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<ApiError> handleInternal(InternalErrorException ex, HttpServletRequest req) {
        ApiError err = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                LocalDateTime.now(),
                req.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

    // 400 - Validation errors from @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        BindingResult br = ex.getBindingResult();
        String messages = br.getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining("; "));

        ApiError err = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                messages,
                LocalDateTime.now(),
                req.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    // Generic fallback - 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(Exception ex, HttpServletRequest req) {
        ApiError err = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage() != null ? ex.getMessage() : "Unexpected error occurred",
                LocalDateTime.now(),
                req.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }
}
