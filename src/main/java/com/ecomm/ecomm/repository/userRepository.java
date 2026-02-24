package com.ecomm.ecomm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.ecomm.ecomm.model.User;

public interface userRepository extends MongoRepository<User, String> {
}
