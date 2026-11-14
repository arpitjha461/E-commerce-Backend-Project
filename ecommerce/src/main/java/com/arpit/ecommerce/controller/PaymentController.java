package com.arpit.ecommerce.controller;

import com.arpit.ecommerce.dto.response.PaymentResponseDTO;
import com.arpit.ecommerce.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@Tag(
        name = "Payment Management",
        description = "APIs for creating and completing payments"
)
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/{orderId}")
    @Operation(
            summary = "Create a payment",
            description = "Creates a payment for an existing order using its unique ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment created successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<PaymentResponseDTO> createPayment(@Parameter(description = "Unique ID of the order", example = "1")@PathVariable Long orderId){

        PaymentResponseDTO responseDTO = paymentService.createPayment(orderId);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{paymentId}/complete")
    @Operation(
            summary = "Complete a payment",
            description = "Marks an existing payment as completed using its unique ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment completed successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public ResponseEntity<PaymentResponseDTO> completePayment(@Parameter(description = "Unique ID of the payment", example = "1")@PathVariable Long paymentId){
        PaymentResponseDTO responseDTO = paymentService.completePayment(paymentId);
        return ResponseEntity.ok(responseDTO);

    }}
