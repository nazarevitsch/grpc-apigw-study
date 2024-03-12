package com.study.grpc.api.gw.dto;

import lombok.Data;

import java.util.List;

@Data
public class PersonView {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private List<PetView> pets;
}
