package com.arpit.ecommerce.service;

import com.arpit.ecommerce.dto.request.PaymentRequestDTO;
import com.arpit.ecommerce.dto.response.PaymentResponseDTO;
import com.arpit.ecommerce.entity.User;
import com.arpit.ecommerce.exception.UserNotFoundException;
import com.arpit.ecommerce.repository.OrderRepository;
import com.arpit.ecommerce.repository.PaymentRepository;
import com.arpit.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public PaymentResponseDTO createPayment(Long orderId, PaymentRequestDTO requestDTO){
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User not found with email: "+email));


    }
}
