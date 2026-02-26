package com.ecomm.ecomm.controller;

import com.ecomm.ecomm.model.User;
import com.ecomm.ecomm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;


    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/healthCheck")
    public String healthCheck() {
        return "User Service is up and running!";
    }



}
