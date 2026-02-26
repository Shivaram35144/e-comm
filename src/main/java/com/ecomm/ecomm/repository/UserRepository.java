package com.ecomm.ecomm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.ecomm.ecomm.model.User;

public interface UserRepository extends MongoRepository<User, String> {
}
