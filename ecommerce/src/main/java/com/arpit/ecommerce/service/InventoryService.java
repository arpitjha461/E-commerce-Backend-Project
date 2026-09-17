package com.arpit.ecommerce.service;

import com.arpit.ecommerce.dto.response.InventoryResponseDTO;
import com.arpit.ecommerce.entity.Inventory;
import com.arpit.ecommerce.exception.InsufficientStockException;
import com.arpit.ecommerce.exception.InvalidReleaseOperationException;
import com.arpit.ecommerce.exception.InvalidSaleOperationException;
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

    @Transactional
    public void releaseReserveStock(Long productId,Integer quantity){
        Inventory inventory = inventoryRepository.findByProductIdForUpdate(productId)
                .orElseThrow(()-> new InventoryNotFoundException("Inventory not found for product Id: " + productId));
        if (inventory.getReservedStock()<quantity){
            throw new InvalidReleaseOperationException("Cannot release more stock than reserved stock");
        }
        inventory.setReservedStock(inventory.getReservedStock()-quantity);
        inventory.setAvailableStock(inventory.getAvailableStock()+quantity);
        inventoryRepository.save(inventory);
    }

    @Transactional
    public void completeSale(Long productId, Integer quantity){
        Inventory inventory = inventoryRepository.findByProductIdForUpdate(productId)
                .orElseThrow(()-> new InventoryNotFoundException("Inventory not found for product Id: " + productId));

        if (inventory.getReservedStock()< quantity){
            throw new InvalidSaleOperationException("Cannot complete sale for more than reserved stock");
        }
        inventory.setReservedStock(inventory.getReservedStock()-quantity);
        inventoryRepository.save(inventory);
    }

    public InventoryResponseDTO getInventory(Long productId) {

        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() ->
                        new InventoryNotFoundException(
                                "Inventory not found for product Id: " + productId));

        InventoryResponseDTO responseDTO = new InventoryResponseDTO();
        responseDTO.setProductId(inventory.getProduct().getId());
        responseDTO.setAvailableStock(inventory.getAvailableStock());
        responseDTO.setReservedStock(inventory.getReservedStock());
        return responseDTO;
    }

}
