package com.arpit.ecommerce.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class ProductRequestDTO {

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "price is required")
    @DecimalMin(value = "0.01", message = "price must be more than zero")
    private BigDecimal price;

    @NotNull(message = "Stock is required")
    @PositiveOrZero(message = "Stock cannot be negative")
    private Integer stock;

    private String imageUrl;

    public @NotBlank(message = "Product name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Product name is required") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Discription is required") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Discription is required") String description) {
        this.description = description;
    }

    public @NotNull(message = "Stock is required") @PositiveOrZero(message = "Stock cannot be negative") Integer getStock() {
        return stock;
    }

    public void setStock(@NotNull(message = "Stock is required") @PositiveOrZero(message = "Stock cannot be negative") Integer stock) {
        this.stock = stock;
    }

    public @NotNull(message = "price is required") @DecimalMin(value = "0.01", message = "price must be more than zero") BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "price is required") @DecimalMin(value = "0.01", message = "price must be more than zero") BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
