package com.arpit.ecommerce;

import com.arpit.ecommerce.dto.request.ProductRequestDTO;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductRequestDTOValidationTest {

    @Test
    void rejectsMissingAndInvalidProductFields() {
        ProductRequestDTO request = new ProductRequestDTO();
        request.setName(" ");
        request.setDescription("");
        request.setPrice(BigDecimal.ZERO);
        request.setStock(-1);

        try (var factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            Set<String> invalidFields = validator.validate(request).stream()
                    .map(violation -> violation.getPropertyPath().toString())
                    .collect(Collectors.toSet());

            assertEquals(Set.of("categoryId", "name", "description", "price", "stock"), invalidFields);
        }
    }

    @Test
    void acceptsProductWithValidRequiredFields() {
        ProductRequestDTO request = new ProductRequestDTO();
        request.setCategoryId(1L);
        request.setName("Keyboard");
        request.setDescription("Mechanical keyboard");
        request.setPrice(new BigDecimal("49.99"));
        request.setStock(5);

        try (var factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            assertTrue(validator.validate(request).isEmpty());
        }
    }
}
