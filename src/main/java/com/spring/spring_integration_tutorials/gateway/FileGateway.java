package com.spring.spring_integration_tutorials.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

import com.spring.spring_integration_tutorials.model.RequestDto;

@MessagingGateway
public interface FileGateway {

    @Gateway(requestChannel = "file-in-channel")
    public Message<RequestDto> readFile(RequestDto requestDto);
}
