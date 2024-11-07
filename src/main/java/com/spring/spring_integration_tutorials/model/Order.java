package com.spring.spring_integration_tutorials.model;

import lombok.Data;

@Data
public class Order {
    private Long id;
    private String itemName;
    private Double price;
    private String orderStatus;
}
