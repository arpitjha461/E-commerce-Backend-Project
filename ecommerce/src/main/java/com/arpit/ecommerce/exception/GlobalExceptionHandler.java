package com.arpit.ecommerce.exception;

import com.arpit.ecommerce.payload.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });

            ApiError apiError = new ApiError();
            apiError.setTimestamp(LocalDateTime.now());
            apiError.setStatus(HttpStatus.BAD_REQUEST.value());
            apiError.setMessage("Validation failed");
            apiError.setErrors(errors);
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
        @ExceptionHandler(InvalidCredentialsException.class)
        public ResponseEntity<ApiError> handleInvalidCredentials(InvalidCredentialsException ex){
            ApiError apiError = new ApiError();

            apiError.setTimestamp(LocalDateTime.now());
            apiError.setStatus((HttpStatus.UNAUTHORIZED.value()));
            apiError.setMessage(ex.getMessage());
            return new ResponseEntity<>(apiError,HttpStatus.UNAUTHORIZED);
        }

        @ExceptionHandler(ProductNotFoundException.class)
        public ResponseEntity<ApiError> handleProductNotFoundException(ProductNotFoundException ex){
            ApiError apiError = new ApiError();
            apiError.setTimestamp(LocalDateTime.now());
            apiError.setStatus(HttpStatus.NOT_FOUND.value());
            apiError.setMessage(ex.getMessage());
            return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
            }

}
