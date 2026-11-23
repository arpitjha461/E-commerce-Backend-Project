package com.arpit.ecommerce;

import com.arpit.ecommerce.dto.request.InventoryRequestDTO;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InventoryRequestDTOValidationTest {

    @Test
    void rejectsMissingAvailableStock() {
        InventoryRequestDTO request = new InventoryRequestDTO();

        try (var factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            assertEquals(1, validator.validate(request).size());
        }
    }

    @Test
    void rejectsNegativeAvailableStock() {
        InventoryRequestDTO request = new InventoryRequestDTO();
        request.setAvailableStock(-1);

        try (var factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            assertEquals(1, validator.validate(request).size());
        }
    }

    @Test
    void acceptsZeroAvailableStock() {
        InventoryRequestDTO request = new InventoryRequestDTO();
        request.setAvailableStock(0);

        try (var factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            assertTrue(validator.validate(request).isEmpty());
        }
    }
}
