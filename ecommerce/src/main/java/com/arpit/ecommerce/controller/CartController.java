package com.arpit.ecommerce.controller;

import com.arpit.ecommerce.dto.request.AddToCartRequestDTO;
import com.arpit.ecommerce.dto.response.CartResponseDTO;
import com.arpit.ecommerce.dto.request.UpdateCartQuantityRequestDTO;
import com.arpit.ecommerce.service.CartService;
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
@RequestMapping("/cart")
@Tag(
        name = "Cart Management",
        description = "APIs for adding, retrieving, updating and removing cart items"
)
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    @Operation(
            summary = "Add product to cart",
            description = "Adds a product with the specified quantity to a user's cart"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product added to cart successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid cart request"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions")
    })
    public ResponseEntity<String> addToCart(@Valid @RequestBody AddToCartRequestDTO requestDTO){
        String response = cartService.addToCart(requestDTO.getUserId(),
        requestDTO.getProductId(),
        requestDTO.getQuantity());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userid}")
    @Operation(
            summary = "Get user's cart",
            description = "Retrieves the cart details for a specific user"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    public ResponseEntity<CartResponseDTO> getCart(@Parameter(description = "Unique ID of the user", example = "1")@PathVariable Long userid){
        return ResponseEntity.ok(cartService.getCart(userid));
    }

    @PutMapping("/items/{cartItemId}")
    @Operation(
            summary = "Update cart item quantity",
            description = "Updates the quantity of an existing item in the user's cart"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart item quantity updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid quantity"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    public ResponseEntity<String> updateCartQuantity(@Parameter(description = "Unique ID of the cart item", example = "1")@PathVariable Long cartItemId,
                  @Valid @RequestBody UpdateCartQuantityRequestDTO requestDTO){
       String response = cartService.updateCartQuantity(cartItemId, requestDTO);
       return ResponseEntity.ok(response);
    }

    @DeleteMapping("/items/{cartItemId}")
    @Operation(
            summary = "Remove item from cart",
            description = "Removes an existing item from the user's cart"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart item removed successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    public ResponseEntity<String> removeCartItem(@Parameter(description = "Unique ID of the cart item", example = "1")@PathVariable Long cartItemId){
        String response = cartService.removeCartItem(cartItemId);
        return ResponseEntity.ok(response);
    }


}
