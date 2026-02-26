package com.ecomm.ecomm.controller;

import com.ecomm.ecomm.model.Cart;
import com.ecomm.ecomm.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartRepository cartRepository;

    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {
        return cartRepository.save(cart);
    }

    @GetMapping
    public Iterable<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @GetMapping("/healthCheck")
    public String healthCheck() {
        return "Cart Service is up and running!";
    }
}

