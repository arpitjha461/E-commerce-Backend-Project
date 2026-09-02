package com.arpit.ecommerce.service;

import com.arpit.ecommerce.dto.request.PaymentRequestDTO;
import com.arpit.ecommerce.dto.response.PaymentResponseDTO;
import com.arpit.ecommerce.entity.Order;
import com.arpit.ecommerce.entity.Payment;
import com.arpit.ecommerce.entity.User;
import com.arpit.ecommerce.enums.OrderStatus;
import com.arpit.ecommerce.enums.PaymentStatus;
import com.arpit.ecommerce.exception.*;
import com.arpit.ecommerce.repository.OrderRepository;
import com.arpit.ecommerce.repository.PaymentRepository;
import com.arpit.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

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

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException("Order not found with orderId: "+ orderId));

        if (!order.getUser().getId().equals(user.getId())){
            throw new UnauthorizedPaymentException("Unauthorized payment for this order");
        }
        if(!OrderStatus.PENDING.equals(order.getStatus())){
            throw new InvalidStatusForPaymentException("Payment is not allowed for order with status: "+
                    order.getStatus());
        }

        if (paymentRepository.findByOrder(order).isPresent()){
            throw new PaymentAlreadyExistsException("Payment already exists for order: "+ orderId);
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(requestDTO.getPaymentMethod());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setTransactionId(UUID.randomUUID().toString());

        paymentRepository.save(payment);

        PaymentResponseDTO responseDTO = new PaymentResponseDTO();
        responseDTO.setOrderId(orderId);
        responseDTO.setPaymentId(payment.getId());
        responseDTO.setPaymentMethod(payment.getPaymentMethod());
        responseDTO.setPaymentStatus(payment.getStatus());
        responseDTO.setTransactionId(payment.getTransactionId());
        responseDTO.setAmount(payment.getAmount());
        responseDTO.setCreatedAt(payment.getCreatedAt());

        return responseDTO;
    }
}













