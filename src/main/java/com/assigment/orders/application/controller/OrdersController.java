package com.assigment.orders.application.controller;

import com.assigment.orders.application.request.ChangeStatusDTO;
import com.assigment.orders.application.request.CreateOrderDTO;
import com.assigment.orders.domain.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("v1/orders")
@AllArgsConstructor
public class OrdersController {

    private final CartService cartService;

    @GetMapping("user/{userUUID}")
    public ResponseEntity<?> getOrdersForUser(@RequestHeader("X-auth-user-id") String user, @RequestHeader("X-auth-user-roles") String roles, @PathVariable String userUUID){
        if(roles.contains("ROLE_ADMIN")){
            return cartService.getOrdersByUser(userUUID);
        }else if(!user.equals(userUUID)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return cartService.getOrdersByUser(user);
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestHeader("X-auth-user-id") String user, @RequestHeader("X-auth-user-roles") String roles){
        if(roles.contains("ROLE_ADMIN")){
            return cartService.getOrders();
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PutMapping("/{orderUUID}/changeStatus")
    public ResponseEntity<?> changeOrderStatus(@RequestHeader("X-auth-user-roles") String roles, @RequestBody ChangeStatusDTO order, @PathVariable String orderUUID){
        if(roles.contains("ROLE_ADMIN")){
            return cartService.changeStatus(orderUUID, order);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    }

    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestHeader("X-auth-user-id") String userUUID, @RequestBody CreateOrderDTO order){
        return cartService.createOrder(order, userUUID);
    }

}
