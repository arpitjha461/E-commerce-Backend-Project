package com.arpit.ecommerce.exception;

import com.arpit.ecommerce.payload.ApiError;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    @Test
    void handleInvalidReleaseOperationReturnsBadRequest() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        InvalidReleaseOperationException exception =
                new InvalidReleaseOperationException("Cannot release stock");

        ResponseEntity<ApiError> response =
                handler.handleInvalidReleaseOperationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals("Cannot release stock", response.getBody().getMessage());
    }
}
