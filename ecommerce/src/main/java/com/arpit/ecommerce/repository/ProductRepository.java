package com.arpit.ecommerce.repository;

import com.arpit.ecommerce.entity.Product;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String nameKeyword, String descriptionKeyword);
}
