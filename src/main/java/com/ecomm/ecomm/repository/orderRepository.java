package com.ecomm.ecomm.repository;

import com.ecomm.ecomm.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface orderRepository extends MongoRepository<Order, String>{
}
