package com.assigment.orders.domain.repository;

import com.assigment.orders.domain.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends MongoRepository<Order, String> {
    Optional<List<Order>> findByUserUUID(String uuid);

}