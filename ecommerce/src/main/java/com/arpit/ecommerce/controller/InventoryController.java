package com.arpit.ecommerce.controller;

import com.arpit.ecommerce.dto.request.InventoryRequestDTO;
import com.arpit.ecommerce.dto.request.ReserveStockRequestDTO;
import com.arpit.ecommerce.dto.response.InventoryResponseDTO;
import com.arpit.ecommerce.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PutMapping("/{id}")
    public ResponseEntity<String> setAvailableStock(@PathVariable Long id,
                                            @Valid @RequestBody InventoryRequestDTO requestDTO){
        String message = inventoryService.setAvailableStock(id,requestDTO.getAvailableStock());
        return ResponseEntity.ok(message);
    }

    @PutMapping("/{productId}/reserve")
    public ResponseEntity<String> setReserveStock(@PathVariable Long productId,
                                                  @Valid @RequestBody ReserveStockRequestDTO requestDTO){
        String message = inventoryService.setReserveStock(productId,requestDTO.getQuantity());
        return ResponseEntity.ok(message);
    }
    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponseDTO> getInventory(
            @PathVariable Long productId) {

        return ResponseEntity.ok(inventoryService.getInventory(productId));
    }

}
