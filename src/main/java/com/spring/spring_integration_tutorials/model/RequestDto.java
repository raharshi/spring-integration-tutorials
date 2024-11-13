package com.spring.spring_integration_tutorials.model;

import lombok.Data;

@Data
public class RequestDto {
    private String filePath;
    private Object data;
    private String status;
}
