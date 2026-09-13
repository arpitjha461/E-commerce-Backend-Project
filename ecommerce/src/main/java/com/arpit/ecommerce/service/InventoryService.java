package com.arpit.ecommerce.service;

import com.arpit.ecommerce.entity.Inventory;
import com.arpit.ecommerce.exception.InsufficientStockException;
import com.arpit.ecommerce.exception.InventoryNotFoundException;
import com.arpit.ecommerce.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public String setAvailableStock(Long productId, Integer availableStock){
        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(
                ()-> new InventoryNotFoundException("Inventory Not found for product Id: "+productId));

        inventory.setAvailableStock(availableStock);
        inventoryRepository.save(inventory);
        return "Stock updated successfully";
    }

    @Transactional
    public String setReserveStock(Long productId, Integer reserveStock){
        Inventory inventory = inventoryRepository.findByProductIdForUpdate(productId)
                .orElseThrow(()-> new InventoryNotFoundException("Inventory Not found for product Id: "+productId));
        if (inventory.getAvailableStock()<reserveStock){
            throw new InsufficientStockException("Insufficient stock for product Id: " + productId);
        }
        inventory.setReservedStock(inventory.getReservedStock()+reserveStock);
        inventory.setAvailableStock(inventory.getAvailableStock()-reserveStock);
        inventoryRepository.save(inventory);
        return "Stock reserved successfully";
    }

}
