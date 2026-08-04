package com.arpit.ecommerce.entity;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

public class Order {

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;           // One Order contains many OrderItems
                                                 // Parent → Children = @OneToMany
                                                // Child → Parent = @ManyToOne

}
