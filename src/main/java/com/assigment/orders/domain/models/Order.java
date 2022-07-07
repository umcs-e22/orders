package com.assigment.orders.domain.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    @Indexed(unique = true)
    private String orderUUID;
    private String cartUUID;
    private String userUUID;

    public Order(String orderUUID,  String cartUUID, String userUUID) {
        this.cartUUID = cartUUID;
        this.orderUUID = orderUUID;
        this.userUUID = userUUID;
    }
}
