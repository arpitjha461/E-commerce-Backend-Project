package com.arpit.ecommerce.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class InventoryRequestDTO {

//    @NotNull(message = "Available stock is required")
//    @Min(value = 0,message = "Available Stock cannot be negative")
    private Integer availableStock;

    public Integer getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Integer availableStock) {
        this.availableStock = availableStock;
    }
}
