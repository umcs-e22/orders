package com.assigment.orders.domain.service;

import com.assigment.orders.application.request.Cart;
import com.assigment.orders.application.request.ChangeStatusDTO;
import com.assigment.orders.application.request.CreateOrderDTO;
import com.assigment.orders.application.response.CartUUIDDTO;
import com.assigment.orders.domain.models.CartStatus;
import com.assigment.orders.domain.models.OrderStatus;
import com.assigment.orders.domain.repository.OrdersRepository;
import com.assigment.orders.domain.models.Order;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class CartService {

    protected final OrdersRepository ordersRepository;

    public ResponseEntity<?> getOrdersByUser(String uuid) {
        log.info("Getting ordersByUser:<"+uuid+">");
        Optional<List<Order>> orders = ordersRepository.findByUserUUID(uuid);
        if(orders.isPresent()){
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> changeStatus(String orderUUID, ChangeStatusDTO status) {
        Optional<Order> order = ordersRepository.findByOrderUUID(orderUUID);
        if(order.isPresent()){
            order.get().setStatus(status.getStatus());
            ordersRepository.save(order.get());
            return new ResponseEntity<>(order, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getOrders() {
        List<Order> orders = ordersRepository.findAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    public ResponseEntity<?> createOrder(CreateOrderDTO order, String userUUID) {
        RestTemplate restTemplate = new RestTemplate();
        String cartResourceUrl = "http://localhost:8457/v1/carts/";
        Cart b = restTemplate.getForObject(cartResourceUrl + order.getCartUUID(), Cart.class);

        if(b!=null && b.getUserUUID().equals(userUUID) && b.getStatus() == CartStatus.OPEN){
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-auth-user-id", userUUID);
            Object object = restTemplate.postForObject(cartResourceUrl+"close", new HttpEntity<>(new CartUUIDDTO(b.getCartUUID()), headers), Object.class);
            if(object != null){
                Order o = new Order(UUID.randomUUID().toString(), b.getCartUUID(), b.getUserUUID(), OrderStatus.NEW, new Date());
                ordersRepository.save(o);
                return new ResponseEntity<>(o, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
