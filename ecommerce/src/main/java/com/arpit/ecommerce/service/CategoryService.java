package com.arpit.ecommerce.service;

import com.arpit.ecommerce.dto.CategoryRequestDTO;
import com.arpit.ecommerce.dto.CategoryResponseDTO;
import com.arpit.ecommerce.entity.Category;
import com.arpit.ecommerce.exception.CategoryNotFoundException;
import com.arpit.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponseDTO addCategory(CategoryRequestDTO requestDTO){
        Category category = new Category();
        updateCategoryFromDTO(category,requestDTO);
        category= categoryRepository.save(category);
        return mapToResponse(category);
    }

    public List<CategoryResponseDTO> getAllCategories(){
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponseDTO> responseDTOList = new ArrayList<>();

        for (Category category : categoryList){
            responseDTOList.add(mapToResponse(category));
        }
        return responseDTOList;
    }

    public CategoryResponseDTO getCategoryById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException("Category not found with id:" + id));
        return mapToResponse(category);
    }

    public CategoryResponseDTO updateCategory(Long id,CategoryRequestDTO requestDTO){
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException("Category not found with id : "+id));
         updateCategoryFromDTO(category,requestDTO);
         category = categoryRepository.save(category);
        return mapToResponse(category);
    }

    public String deleteCategory(Long id){
       categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException("category not found with id: "+id));
        categoryRepository.deleteById(id);
        return "Category having id: "+ id +"deleted successfully";
    }

    private void updateCategoryFromDTO(Category category,CategoryRequestDTO requestDTO){
        category.setName(requestDTO.getName());
        category.setDescription(requestDTO.getDescription());
    }
    private CategoryResponseDTO mapToResponse(Category category){
         CategoryResponseDTO responseDTO = new CategoryResponseDTO();
         responseDTO.setId(category.getId());
         responseDTO.setName(category.getName());
         responseDTO.setDescription(category.getDescription());
         responseDTO.setCreatedAt(category.getCreatedAt());
         responseDTO.setUpdatedAt(category.getUpdatedAt());
         return responseDTO;
    }
}
