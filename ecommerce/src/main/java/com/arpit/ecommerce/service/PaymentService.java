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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

        return mapToPaymentResponseDTO(payment);
    }

    @Transactional
    public PaymentResponseDTO completePayment(Long paymentId){
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(()-> new PaymentNotFoundException("Payment not found with Id: "+ paymentId));

        if (!PaymentStatus.PENDING.equals(payment.getStatus())){
            throw new InvalidStatusForPaymentException("Payment cannot be completed in status: "+payment.getStatus());
        }
        payment.setStatus(PaymentStatus.SUCCESS);

        Order order = payment.getOrder();
        if (!OrderStatus.PENDING.equals(order.getStatus())){
            throw new InvalidOrderStatusException("Payment cannot be completed for order with status: "
                    + order.getStatus());
        }
        order.setStatus(OrderStatus.CONFIRMED);
        paymentRepository.save(payment);

        return mapToPaymentResponseDTO(payment);
    }

    private PaymentResponseDTO mapToPaymentResponseDTO(Payment payment){
        PaymentResponseDTO responseDTO = new PaymentResponseDTO();

        responseDTO.setOrderId(payment.getOrder().getId());
        responseDTO.setPaymentId(payment.getId());
        responseDTO.setPaymentMethod(payment.getPaymentMethod());
        responseDTO.setPaymentStatus(payment.getStatus());
        responseDTO.setTransactionId(payment.getTransactionId());
        responseDTO.setAmount(payment.getAmount());
        responseDTO.setCreatedAt(payment.getCreatedAt());
        return responseDTO;
    }
}













