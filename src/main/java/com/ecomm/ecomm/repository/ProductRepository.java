package com.ecomm.ecomm.repository;

import com.ecomm.ecomm.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProductRepository extends MongoRepository<Product, String> {
}
