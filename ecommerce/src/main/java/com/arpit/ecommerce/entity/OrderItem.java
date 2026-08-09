package com.arpit.ecommerce.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;        //One Order can be associated with many OrderItems.

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;   //One Product can be associated with many OrderItems

    private Integer quantity;
    private BigDecimal price;

}
