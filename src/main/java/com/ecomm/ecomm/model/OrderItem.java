package com.ecomm.ecomm.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    private String productId;
    private String productName;
    private double price;
    private int quantity;
}
