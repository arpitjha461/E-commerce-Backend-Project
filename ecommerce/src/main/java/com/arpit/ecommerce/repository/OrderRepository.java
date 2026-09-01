package com.arpit.ecommerce.repository;

import com.arpit.ecommerce.entity.Order;
import com.arpit.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUser(User user);
}
