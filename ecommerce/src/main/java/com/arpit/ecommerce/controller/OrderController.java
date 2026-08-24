package com.arpit.ecommerce.controller;

import com.arpit.ecommerce.dto.response.OrderResponseDTO;
import com.arpit.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<OrderResponseDTO> placeOrder(){
        OrderResponseDTO responseDTO = orderService.placeOrder();
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(){
        List<OrderResponseDTO> orders =orderService.getMyOrders();
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/{orderid}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long orderId){
        OrderResponseDTO responseDTO = orderService.getOrderById(orderId);
        return ResponseEntity.ok(responseDTO);
    }
//      or another way
//    @GetMapping("/{order-id}")
//    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable("order-id") Long orderId){
//        OrderResponseDTO responseDTO = orderService.getOrderById(orderId);
//        return ResponseEntity.ok(responseDTO);
//     }
}
