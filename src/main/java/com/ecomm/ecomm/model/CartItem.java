package com.ecomm.ecomm.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    private String productId;
    private int quantity;
}
