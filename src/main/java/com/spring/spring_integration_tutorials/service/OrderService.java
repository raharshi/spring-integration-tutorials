package com.spring.spring_integration_tutorials.service;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import com.spring.spring_integration_tutorials.model.Order;

@Service
public class OrderService {

    // @Bean(name = "process-order-channel")
    // public MessageChannel processOrderchannel(){
    //     /*By default spring integration uses direct channel 
    //     if we want to create a different channel we can use this process */
    //     return new DirectChannel();
    // }

    @Bean(name="process-order-channel")
    public MessageChannel processChannel(){
        return new QueueChannel(10);
    }
    @Bean(name="reply-order-channel")
    public MessageChannel replyChannel(){
        return new QueueChannel(10);
    }

    @ServiceActivator(inputChannel = "request-in-channel",outputChannel = "process-order-channel")
    public Message<Order> placeOrder(Message<Order> order){
        return order;
    }

    @ServiceActivator(inputChannel = "process-order-channel", outputChannel = "reply-order-channel", poller = @Poller(fixedDelay = "100",maxMessagesPerPoll = "1"))
    public Message<Order> processOrder(Message<Order> order){
        order.getPayload().setOrderStatus("Order placed successfully!!");
        return order;
    }

    @ServiceActivator(inputChannel = "reply-order-channel", poller = @Poller(fixedDelay = "100",maxMessagesPerPoll = "1"))
    public void replyOrder(Message<Order> order){
        if(order.getHeaders().getReplyChannel() instanceof MessageChannel replyChannel){
            replyChannel.send(order);
        }
    }

}
