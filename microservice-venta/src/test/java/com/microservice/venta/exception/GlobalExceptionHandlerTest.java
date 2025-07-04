package com.microservice.venta.exception;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    @Test
    void handleEntityNotFound_ReturnsNotFound() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        EntityNotFoundException ex = new EntityNotFoundException("No encontrado");
        ResponseEntity<String> response = handler.handleEntityNotFound(ex);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("No encontrado", response.getBody());
    }
}