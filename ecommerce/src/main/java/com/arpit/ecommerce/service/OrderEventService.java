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

    public void createOrderPlacedEvent(Long userId, Long orderId){

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setEventType("ORDER_PLACED");
        orderEvent.setOrderId(orderId);
        orderEvent.setStatus("PENDING");
        orderEvent.setUserId(userId);
        orderEvent.setTimestamp(LocalDateTime.now());

        orderEventRepository.save(orderEvent);

    }
}
