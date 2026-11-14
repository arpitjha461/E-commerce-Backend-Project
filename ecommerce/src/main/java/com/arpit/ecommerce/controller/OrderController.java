package com.arpit.ecommerce.controller;

import com.arpit.ecommerce.dto.request.PlaceOrderRequestDTO;
import com.arpit.ecommerce.dto.request.UpdateOrderStatusRequestDTO;
import com.arpit.ecommerce.dto.response.OrderResponseDTO;
import com.arpit.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(
        name = "Order Management",
        description = "APIs for placing, retrieving, cancelling and updating orders"
)
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    @Operation(
            summary = "Place a new order",
            description = "Creates a new order using the provided order details"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order placed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions")
    })
    public ResponseEntity<OrderResponseDTO> placeOrder(@Valid @RequestBody PlaceOrderRequestDTO requestDTO){
        OrderResponseDTO responseDTO = orderService.placeOrder(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/my-orders")
    @Operation(
            summary = "Get my orders",
            description = "Retrieves all orders belonging to the currently authenticated user"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions")
    })
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(){
        List<OrderResponseDTO> orders =orderService.getMyOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    @Operation(
            summary = "Get order by ID",
            description = "Retrieves order details using the unique order ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderResponseDTO> getOrderById(@Parameter(description = "Unique ID of the order", example = "1")@PathVariable Long orderId){
        OrderResponseDTO responseDTO = orderService.getOrderById(orderId);
        return ResponseEntity.ok(responseDTO);
    }
//      or another way
//    @GetMapping("/{order-id}")
//    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable("order-id") Long orderId){
//        OrderResponseDTO responseDTO = orderService.getOrderById(orderId);
//        return ResponseEntity.ok(responseDTO);
//     }

    @PutMapping("/{orderId}/cancel")
    @Operation(
            summary = "Cancel an order",
            description = "Cancels an existing order using its unique ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order cancelled successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderResponseDTO> cancelOrder(@Parameter(description = "Unique ID of the order", example = "1")@PathVariable Long orderId){
        OrderResponseDTO responseDTO = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{orderId}/status")
    @Operation(
            summary = "Update order status",
            description = "Updates the status of an existing order using its unique ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order status"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @Parameter(description = "Unique ID of the order", example = "1")
            @PathVariable Long orderId,@Valid @RequestBody UpdateOrderStatusRequestDTO requestDTO) {
        OrderResponseDTO responseDTO = orderService.updateOrderStatus(orderId, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
