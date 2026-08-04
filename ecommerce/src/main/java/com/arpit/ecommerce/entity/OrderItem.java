package com.arpit.ecommerce.entity;

import jakarta.persistence.ManyToOne;

public class OrderItem {

    @ManyToOne
    private Order order;        //One Order can be associated with many OrderItems.

    @ManyToOne
    private Product product;   //One Product can be associated with many OrderItems
}
