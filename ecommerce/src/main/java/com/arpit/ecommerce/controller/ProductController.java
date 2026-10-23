package com.arpit.ecommerce.controller;

import com.arpit.ecommerce.dto.request.ProductRequestDTO;
import com.arpit.ecommerce.dto.response.ProductResponseDTO;
import com.arpit.ecommerce.service.ProductService;
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
@RequestMapping("/products")
@Tag(
        name = "Product Management",
        description = "APIs for creating, retrieving, updating and deleting products"
)
public class ProductController {

    @Autowired
    private ProductService productService;
    @PostMapping
    @Operation(summary = "Add a product",
            description = "Creates a new product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions")
    })
    public ProductResponseDTO addProduct(@Valid @RequestBody ProductRequestDTO requestDTO){
        return productService.addProduct(requestDTO);
    }
    @GetMapping
    @Operation(summary = "Get all products",
            description = "Retrieves the list of products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions")
    })
    public List<ProductResponseDTO> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID",
            description = "Retrieves a product by its unique ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ProductResponseDTO getProductById(
            @Parameter(description = "Unique ID of the product to retrieve", example = "1")
            @PathVariable Long id){
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product",
            description = "Updates an existing product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ProductResponseDTO updateProduct(
            @Parameter(description = "Unique ID of the product to update", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO requestDTO){
        return productService.updateProduct(id,requestDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product",
            description = "Deletes an existing product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public String deleteProduct(
            @Parameter(description = "Unique ID of the product to delete", example = "1")
            @PathVariable Long id){
        return productService.deleteProduct(id);
    }
}
