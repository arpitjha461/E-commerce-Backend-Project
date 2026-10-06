package com.arpit.ecommerce.repository;

import com.arpit.ecommerce.entity.OrderEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderEventRepository extends MongoRepository<OrderEvent,String> {

}
