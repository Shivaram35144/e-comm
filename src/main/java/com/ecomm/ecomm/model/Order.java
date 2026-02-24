package com.ecomm.ecomm.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private String id;

    private String userId;

    private List<OrderItem> items;

    private double totalAmount;

    private String status; // CREATED, PAID, SHIPPED, DELIVERED

    private LocalDateTime createdAt;
    
}
