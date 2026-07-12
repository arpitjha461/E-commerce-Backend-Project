package com.arpit.ecommerce.controller;

import com.arpit.ecommerce.dto.CategoryRequestDTO;
import com.arpit.ecommerce.dto.CategoryResponseDTO;
import com.arpit.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public CategoryResponseDTO addCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO){
      return categoryService.addCategory(categoryRequestDTO);
    }

    @GetMapping
    public List<CategoryResponseDTO> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public CategoryResponseDTO updateCategory(@PathVariable Long id,@Valid @RequestBody CategoryRequestDTO requestDTO){
        return categoryService.updateCategory(id,requestDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }
}
