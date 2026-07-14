package com.arpit.ecommerce.service;

import com.arpit.ecommerce.dto.ProductRequestDTO;
import com.arpit.ecommerce.dto.ProductResponseDTO;
import com.arpit.ecommerce.entity.Category;
import com.arpit.ecommerce.entity.Product;
import com.arpit.ecommerce.exception.CategoryNotFoundException;
import com.arpit.ecommerce.exception.ProductNotFoundException;
import com.arpit.ecommerce.repository.CategoryRepository;
import com.arpit.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public ProductResponseDTO addProduct(ProductRequestDTO requestDTO){
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(()-> new CategoryNotFoundException("Category not found with Id :"+ requestDTO.getCategoryId()));
        Product product = mapToEntity(requestDTO);
        product.setCategory(category);
        product = productRepository.save(product);
        return mapToResponseDTO(product);
    }

    public List<ProductResponseDTO> getAllProducts(){
        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> responseDTOList = new ArrayList<>();
        for (Product product :products){
            responseDTOList.add(mapToResponseDTO(product));
        }
        return  responseDTOList;
    }
    public ProductResponseDTO getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found with id: " + id));

        return mapToResponseDTO(product);
    }
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found with id: " + id));
        Category category =categoryRepository.findById(requestDTO.getCategoryId())
                        .orElseThrow(()-> new CategoryNotFoundException("Category not found with id: "+ requestDTO.getCategoryId()));
        updateProductFromDTO(product,requestDTO);
        product.setCategory(category);
        product = productRepository.save(product);
        return mapToResponseDTO(product);
    }
    public  String deleteProduct(Long id){
        productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found with id: " + id));
        productRepository.deleteById(id);
        return "Product having id: "+id +"deleted successfully";
    }
    private ProductResponseDTO mapToResponseDTO(Product product){
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setImageUrl(product.getImageUrl());
        if (product.getCategory()!=null) {
            dto.setCategoryId(product.getCategory().getId());
        }

        return dto;
    }
    private Product mapToEntity(ProductRequestDTO requestDTO){
        Product product = new Product();
        product.setName(requestDTO.getName());
        product.setDescription(requestDTO.getDescription());
        product.setPrice(requestDTO.getPrice());
        product.setStock(requestDTO.getStock());
        product.setImageUrl(requestDTO.getImageUrl());

        return product;
    }
    private void updateProductFromDTO(Product product,
                                      ProductRequestDTO requestDTO) {

        product.setName(requestDTO.getName());
        product.setDescription(requestDTO.getDescription());
        product.setPrice(requestDTO.getPrice());
        product.setStock(requestDTO.getStock());
        product.setImageUrl(requestDTO.getImageUrl());
    }
}
