package com.arpit.ecommerce.service;

import com.arpit.ecommerce.entity.Inventory;
import com.arpit.ecommerce.exception.InventoryNotFoundException;
import com.arpit.ecommerce.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public void setAvailableStock(Long productId, Integer availableStock){
        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(
                ()-> new InventoryNotFoundException("Inventory Not found for product Id: "+productId));

        inventory.setAvailableStock(availableStock);
        inventoryRepository.save(inventory);



    }
}
