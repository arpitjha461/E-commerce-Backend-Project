package com.arpit.ecommerce.repository;

import com.arpit.ecommerce.entity.Order;
import com.arpit.ecommerce.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
        Optional<Payment> findByOrder(Order order);
}
