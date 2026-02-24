package com.ecomm.ecomm.repository;

import com.ecomm.ecomm.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface productRepository extends MongoRepository<Product, String> {
}
