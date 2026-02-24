package com.ecomm.ecomm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.ecomm.ecomm.model.Cart;

public interface cartRepository extends MongoRepository<Cart, String> {
}
