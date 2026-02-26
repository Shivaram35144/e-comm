package com.ecomm.ecomm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.ecomm.ecomm.model.Cart;

public interface CartRepository extends MongoRepository<Cart, String> {
}
