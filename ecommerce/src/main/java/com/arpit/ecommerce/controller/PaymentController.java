package com.arpit.ecommerce.controller;

import com.arpit.ecommerce.dto.request.PaymentRequestDTO;
import com.arpit.ecommerce.dto.response.PaymentResponseDTO;
import com.arpit.ecommerce.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/{orderId}")
    public ResponseEntity<PaymentResponseDTO> createPayment(@PathVariable Long orderId,
                                                            @Valid @RequestBody PaymentRequestDTO requestDTO){
        PaymentResponseDTO responseDTO = paymentService.createPayment(orderId,requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
