package com.arpit.ecommerce.service;

import com.arpit.ecommerce.entity.OrderEvent;
import com.arpit.ecommerce.repository.OrderEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderEventService {
    @Autowired
    private OrderEventRepository orderEventRepository;

    public void createOrderPlacedEvent(Long userId, Long orderId, String status){

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setEventType("ORDER_PLACED");
        orderEvent.setOrderId(orderId);
        orderEvent.setStatus(status);
        orderEvent.setUserId(userId);
        orderEvent.setTimestamp(LocalDateTime.now());

        orderEventRepository.save(orderEvent);
    }

    public void createOrderStatusChangedEvent(Long userId, Long orderId, String status){
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setEventType("ORDER_STATUS_CHANGED");
        orderEvent.setUserId(userId);
        orderEvent.setOrderId(orderId);
        orderEvent.setStatus(status);
        orderEvent.setTimestamp(LocalDateTime.now());

        orderEventRepository.save(orderEvent);
    }
}













