package com.arpit.ecommerce.controller;

import com.arpit.ecommerce.dto.ProductRequestDTO;
import com.arpit.ecommerce.dto.ProductResponseDTO;
import com.arpit.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductResponseDTO addProduct(@Valid @RequestBody ProductRequestDTO requestDTO){
        return productService.addProduct(requestDTO);
    }
    @GetMapping
    public List<ProductResponseDTO> getAllProducts(){
        return productService.getAllProducts();
    }
    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }
    @PutMapping("/{id}")
    public ProductResponseDTO updateProduct(@PathVariable Long id,
                                            @Valid @RequestBody ProductRequestDTO requestDTO){
        return productService.updateProduct(id,requestDTO);
    }
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id){
        return productService.deleteProduct(id);
    }
}
