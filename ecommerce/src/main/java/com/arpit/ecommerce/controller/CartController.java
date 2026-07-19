package com.arpit.ecommerce.controller;

import com.arpit.ecommerce.dto.AddToCartRequestDTO;
import com.arpit.ecommerce.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
