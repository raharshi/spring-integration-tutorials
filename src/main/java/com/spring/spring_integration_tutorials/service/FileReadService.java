package com.spring.spring_integration_tutorials.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.spring_integration_tutorials.model.RequestDto;

@Service
public class FileReadService {


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
            String filePath = request.getPayload().getFilePath();
            File file = new File(filePath);
            
            if (file.exists() && file.isFile()) {
                try {
                    // Read file content as a JSON string
                    ObjectMapper objectMapper = new ObjectMapper();
                    Object data = objectMapper.readValue(file, Object.class);

                    // String jsonData = Files.readString(Paths.get(filePath));
                    request.getPayload().setData(data);
                    request.getPayload().setStatus("Request placed successfully!!");
                } catch (Exception e) {
                    throw new RuntimeException("Error reading file: " + e.getMessage());
                }
            } else {
                // throw new RuntimeException("File not found or invalid path: " + filePath);
                request.getPayload().setStatus("Request Failed!!");
            }
            
        return request;
    }

    @ServiceActivator(inputChannel = "reply-channel", poller = @Poller(fixedDelay = "100",maxMessagesPerPoll = "1"))
    public void replyrequest(Message<RequestDto> request){
        if(request.getHeaders().getReplyChannel() instanceof MessageChannel replyChannel){
            replyChannel.send(request);
        }
    }

}
