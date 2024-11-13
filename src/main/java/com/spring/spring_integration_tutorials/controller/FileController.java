package com.spring.spring_integration_tutorials.controller;

import org.springframework.web.bind.annotation.RestController;

import com.spring.spring_integration_tutorials.gateway.FileGateway;
import com.spring.spring_integration_tutorials.model.RequestDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    FileGateway fileGateway;

    @PostMapping("/readFile")
    public RequestDto readeFile(@RequestBody RequestDto requestDto) {
       return fileGateway.readFile(requestDto).getPayload();
    }
    


}
