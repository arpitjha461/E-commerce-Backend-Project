package com.arpit.ecommerce.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class ProductRequestDTO {

    @Schema(description = "ID of the category this product belongs to", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Category is required")
    private Long categoryId;

    @Schema(description = "Display name of the product", example = "Mechanical Keyboard", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Product name is required")
    private String name;

    @Schema(description = "Detailed description of the product", example = "RGB mechanical keyboard with blue switches", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Description is required")
    private String description;

    @Schema(description = "Product price; must be at least 0.01", example = "49.99", minimum = "0.01", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be more than zero")
    private BigDecimal price;

    @Schema(description = "Available inventory quantity; cannot be negative", example = "25", minimum = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Stock is required")
    @PositiveOrZero(message = "Stock cannot be negative")
    private Integer stock;

    @Schema(description = "Optional URL of the product image", example = "https://example.com/images/keyboard.jpg")
    private String imageUrl;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
