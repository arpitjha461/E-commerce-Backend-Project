package com.arpit.ecommerce.service;

import com.arpit.ecommerce.dto.request.ProductRequestDTO;
import com.arpit.ecommerce.dto.response.ProductResponseDTO;
import com.arpit.ecommerce.entity.Category;
import com.arpit.ecommerce.entity.Inventory;
import com.arpit.ecommerce.entity.Product;
import com.arpit.ecommerce.repository.CategoryRepository;
import com.arpit.ecommerce.repository.InventoryRepository;
import com.arpit.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void addProductCreatesProductAndInitializesInventory() {
        Category category = new Category();
        category.setId(7L);

        ProductRequestDTO request = new ProductRequestDTO();
        request.setCategoryId(7L);
        request.setName("Mechanical Keyboard");
        request.setDescription("RGB mechanical keyboard");
        request.setPrice(new BigDecimal("49.99"));
        request.setStock(12);
        request.setImageUrl("https://example.com/keyboard.jpg");

        when(categoryRepository.findById(7L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            savedProduct.setId(101L);
            return savedProduct;
        });

        ProductResponseDTO response = productService.addProduct(request);

        assertEquals(101L, response.getId());
        assertEquals("Mechanical Keyboard", response.getName());
        assertEquals(new BigDecimal("49.99"), response.getPrice());
        assertEquals(12, response.getStock());
        assertEquals(7L, response.getCategoryId());
        assertEquals("https://example.com/keyboard.jpg", response.getImageUrl());

        ArgumentCaptor<Inventory> inventoryCaptor = ArgumentCaptor.forClass(Inventory.class);
        verify(inventoryRepository).save(inventoryCaptor.capture());

        Inventory createdInventory = inventoryCaptor.getValue();
        assertNotNull(createdInventory.getProduct());
        assertEquals(101L, createdInventory.getProduct().getId());
        assertEquals(12, createdInventory.getAvailableStock());
        assertEquals(0, createdInventory.getReservedStock());
    }
}
