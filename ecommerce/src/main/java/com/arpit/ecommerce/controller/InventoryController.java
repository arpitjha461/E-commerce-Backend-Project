package com.arpit.ecommerce.controller;

import com.arpit.ecommerce.dto.request.InventoryRequestDTO;
import com.arpit.ecommerce.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PutMapping("/{id}")
    public void setAvailableStock(@PathVariable Long id,
                                  @Valid @RequestBody InventoryRequestDTO requestDTO){
        inventoryService.setAvailableStock(id,requestDTO.getAvailableStock());
    }

}
