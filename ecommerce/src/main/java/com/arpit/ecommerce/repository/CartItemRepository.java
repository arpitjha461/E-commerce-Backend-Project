package com.arpit.ecommerce.repository;

import com.arpit.ecommerce.entity.Cart;
import com.arpit.ecommerce.entity.CartItem;
import com.arpit.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
