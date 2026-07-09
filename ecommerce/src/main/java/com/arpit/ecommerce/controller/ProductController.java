package com.arpit.ecommerce.controller;

import com.arpit.ecommerce.dto.ProductRequestDTO;
import com.arpit.ecommerce.dto.ProductResponseDTO;
import com.arpit.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductResponseDTO addProduct(@Valid @RequestBody ProductRequestDTO requestDTO){
        return productService.addProduct(requestDTO);
    }


}
