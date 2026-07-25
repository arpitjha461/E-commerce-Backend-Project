package com.arpit.ecommerce.controller;

import com.arpit.ecommerce.dto.AddToCartRequestDTO;
import com.arpit.ecommerce.dto.CartResponseDTO;
import com.arpit.ecommerce.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@Valid @RequestBody AddToCartRequestDTO requestDTO){
        String response = cartService.addToCart(requestDTO.getUserId(),
        requestDTO.getProductId(),
        requestDTO.getQuantity());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userid}")
    public ResponseEntity<CartResponseDTO> getCart(@PathVariable Long userid){
        return ResponseEntity.ok(cartService.getCart(userid));
    }
}
