package com.arpit.ecommerce.controller;

import com.arpit.ecommerce.dto.response.OrderResponseDTO;
import com.arpit.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
