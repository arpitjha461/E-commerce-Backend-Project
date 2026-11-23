package com.arpit.ecommerce.service;

import com.arpit.ecommerce.dto.response.CategoryResponseDTO;
import com.arpit.ecommerce.entity.Category;
import com.arpit.ecommerce.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void searchCategoriesMatchesNameOrDescriptionIgnoringCase() {
        Category category = new Category();
        category.setId(12L);
        category.setName("Electronics");
        category.setDescription("Devices and accessories");

        when(categoryRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                "ELECTRO", "ELECTRO")).thenReturn(List.of(category));

        List<CategoryResponseDTO> results = categoryService.searchCategories("  ELECTRO ");

        assertEquals(1, results.size());
        assertEquals(12L, results.get(0).getId());
        assertEquals("Electronics", results.get(0).getName());
        assertEquals("Devices and accessories", results.get(0).getDescription());
        verify(categoryRepository).findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                "ELECTRO", "ELECTRO");
    }

    @Test
    void searchCategoriesReturnsEmptyListForBlankKeywordWithoutQueryingRepository() {
        assertEquals(List.of(), categoryService.searchCategories("   "));

        verify(categoryRepository, never())
                .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        anyString(), anyString());
    }
}
