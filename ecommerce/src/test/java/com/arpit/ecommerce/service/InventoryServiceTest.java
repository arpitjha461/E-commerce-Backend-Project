package com.arpit.ecommerce.service;

import com.arpit.ecommerce.exception.InsufficientStockException;
import com.arpit.ecommerce.exception.InvalidReleaseOperationException;
import com.arpit.ecommerce.exception.InvalidSaleOperationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class InventoryServiceTest {

    private final InventoryService inventoryService = new InventoryService();

    @Test
    void reserveStockRejectsZeroQuantity() {
        assertThrows(InsufficientStockException.class,
                () -> inventoryService.setReserveStock(1L, 0));
    }

    @Test
    void reserveStockRejectsNegativeQuantity() {
        assertThrows(InsufficientStockException.class,
                () -> inventoryService.setReserveStock(1L, -1));
    }

    @Test
    void releaseReserveStockRejectsZeroQuantity() {
        assertThrows(InvalidReleaseOperationException.class,
                () -> inventoryService.releaseReserveStock(1L, 0));
    }

    @Test
    void releaseReserveStockRejectsNegativeQuantity() {
        assertThrows(InvalidReleaseOperationException.class,
                () -> inventoryService.releaseReserveStock(1L, -1));
    }

    @Test
    void completeSaleRejectsZeroQuantity() {
        assertThrows(InvalidSaleOperationException.class,
                () -> inventoryService.completeSale(1L, 0));
    }

    @Test
    void completeSaleRejectsNegativeQuantity() {
        assertThrows(InvalidSaleOperationException.class,
                () -> inventoryService.completeSale(1L, -1));
    }
}
