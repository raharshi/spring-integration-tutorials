package com.spring.spring_integration_tutorials.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

import com.spring.spring_integration_tutorials.model.Order;

@MessagingGateway
public interface OrderGateway {

    @Gateway(requestChannel = "request-in-channel")
    public Message<Order> placeOrder(Order order);
}
