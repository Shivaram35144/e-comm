package com.ecomm.ecomm.controller;

import com.ecomm.ecomm.model.Product;
import com.ecomm.ecomm.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @GetMapping
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/healthCheck")
    public String healthCheck() {
        return "Product Service is up and running!";
    }
}

