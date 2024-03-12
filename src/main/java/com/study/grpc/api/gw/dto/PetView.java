package com.study.grpc.api.gw.dto;

import lombok.Data;

@Data
public class PetView {
    private Long id;
    private String name;
    private Long ownerId;
}
