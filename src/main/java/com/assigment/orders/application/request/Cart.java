package com.assigment.orders.application.request;

import com.assigment.orders.domain.models.CartStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private String id;
    private String userUUID;
    private Map<String, Integer> booksUUID;
    private Float price;
    private CartStatus status;
    private String cartUUID;
}
