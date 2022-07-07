package com.assigment.orders.domain.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    @Indexed(unique = true)
    private String orderUUID;
    private String cartUUID;
    private String userUUID;
    private OrderStatus status;
    private Date createDate;

    public Order(String orderUUID,  String cartUUID, String userUUID, OrderStatus status, Date createDate) {
        this.cartUUID = cartUUID;
        this.orderUUID = orderUUID;
        this.userUUID = userUUID;
        this.status = status;
        this.createDate = createDate;
    }
}
