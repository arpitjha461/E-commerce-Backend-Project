package com.arpit.ecommerce.controller;

import com.arpit.ecommerce.dto.request.InventoryRequestDTO;
import com.arpit.ecommerce.dto.request.ReserveStockRequestDTO;
import com.arpit.ecommerce.dto.response.InventoryResponseDTO;
import com.arpit.ecommerce.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@Tag(
        name = "Inventory Management",
        description = "APIs for updating stock, reserving stock and retrieving product inventory"
)
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PutMapping("/{id}")
    @Operation(
            summary = "Set available stock",
            description = "Sets the available stock quantity for a product. This operation requires the ADMIN role."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Available stock updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request: availableStock is required and cannot be negative"),
            @ApiResponse(responseCode = "401", description = "Authentication is required"),
            @ApiResponse(responseCode = "403", description = "The caller does not have the ADMIN role"),
            @ApiResponse(responseCode = "404", description = "Inventory was not found for the product")
    })
    public ResponseEntity<String> setAvailableStock(
            @Parameter(description = "ID of the product whose available stock is being updated", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody InventoryRequestDTO requestDTO) {
        String message = inventoryService.setAvailableStock(id, requestDTO.getAvailableStock());
        return ResponseEntity.ok(message);
    }

    @PutMapping("/{productId}/reserve")
    @Operation(
            summary = "Reserve product stock",
            description = "Moves the requested quantity from available stock to reserved stock. This operation requires the ADMIN role."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock reserved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid quantity or insufficient available stock"),
            @ApiResponse(responseCode = "401", description = "Authentication is required"),
            @ApiResponse(responseCode = "403", description = "The caller does not have the ADMIN role"),
            @ApiResponse(responseCode = "404", description = "Inventory was not found for the product")
    })
    public ResponseEntity<String> setReserveStock(
            @Parameter(description = "ID of the product whose stock is being reserved", example = "1")
            @PathVariable Long productId,
            @Valid @RequestBody ReserveStockRequestDTO requestDTO) {
        String message = inventoryService.setReserveStock(productId, requestDTO.getQuantity());
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{productId}")
    @Operation(
            summary = "Get product inventory",
            description = "Retrieves the available and reserved stock quantities for a product. Authentication is required."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inventory retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication is required"),
            @ApiResponse(responseCode = "404", description = "Inventory was not found for the product")
    })
    public ResponseEntity<InventoryResponseDTO> getInventory(
            @Parameter(description = "ID of the product whose inventory is being retrieved", example = "1")
            @PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getInventory(productId));
    }
}
