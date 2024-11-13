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
import com.spring.spring_integration_tutorials.model.RequestDto;

@Service
public class FileReadService {

    // @Bean(name = "process-order-channel")
    // public MessageChannel processOrderchannel(){
    //     /*By default spring integration uses direct channel 
    //     if we want to create a different channel we can use this process */
    //     return new DirectChannel();
    // }

    @Bean(name="process-data-channel")
    public MessageChannel processChannel(){
        return new QueueChannel(10);
    }
    @Bean(name="reply-channel")
    public MessageChannel replyChannel(){
        return new QueueChannel(10);
    }

    @ServiceActivator(inputChannel = "file-in-channel",outputChannel = "process-data-channel")
    public Message<RequestDto> placeOrder(Message<RequestDto> request){
        return request;
    }

    @ServiceActivator(inputChannel = "process-data-channel", outputChannel = "reply-channel", poller = @Poller(fixedDelay = "100",maxMessagesPerPoll = "1"))
    public Message<RequestDto> processData(Message<RequestDto> request){
        request.getPayload().setStatus("Request placed successfully!!");
        return request;
    }

    @ServiceActivator(inputChannel = "reply-channel", poller = @Poller(fixedDelay = "100",maxMessagesPerPoll = "1"))
    public void replyrequest(Message<RequestDto> request){
        if(request.getHeaders().getReplyChannel() instanceof MessageChannel replyChannel){
            replyChannel.send(request);
        }
    }

}
