package com.arpit.ecommerce.controller;

import com.arpit.ecommerce.dto.request.CategoryRequestDTO;
import com.arpit.ecommerce.dto.response.CategoryResponseDTO;
import com.arpit.ecommerce.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(
        name = "Category Management",
        description = "APIs for creating, retrieving, updating and deleting product categories"
)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(
            summary = "Create a category",
            description = "Create a new product category"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid category data"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions")
    })
    @PostMapping
    public CategoryResponseDTO addCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO){
      return categoryService.addCategory(categoryRequestDTO);
    }

    @GetMapping
    @Operation(
            summary = "Get all categories",
            description = "Retrieves a list of all product categories"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions")
    })
    public List<CategoryResponseDTO> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get category by ID",
            description = "Retrieves a product category using its unique ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public CategoryResponseDTO getCategoryById(@Parameter(description = "Unique ID of the category", example = "1")@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a category",
            description = "Updates an existing product category using its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid category data"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public CategoryResponseDTO updateCategory(@Parameter(description = "Unique ID of the category", example = "1")@PathVariable Long id,@Valid @RequestBody CategoryRequestDTO requestDTO){
        return categoryService.updateCategory(id,requestDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a category",
            description = "Deletes an existing product category using its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public String deleteCategory(@Parameter(description = "Unique ID of the category", example = "1")@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }
}
