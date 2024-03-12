package com.study.grpc.api.gw.dto;

import lombok.Data;

@Data
public class OrderView {
    private Long id;
    private String brand;
    private String state;
    private int price;
}
