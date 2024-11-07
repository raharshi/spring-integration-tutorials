package com.spring.spring_integration_tutorials.controller;

import org.springframework.web.bind.annotation.RestController;

import com.spring.spring_integration_tutorials.gateway.OrderGateway;
import com.spring.spring_integration_tutorials.model.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderGateway orderGateway;

    @PostMapping("/placeOrder")
    public Order placeOrder(@RequestBody Order order) {
       return orderGateway.placeOrder(order).getPayload();
    }
    


}
