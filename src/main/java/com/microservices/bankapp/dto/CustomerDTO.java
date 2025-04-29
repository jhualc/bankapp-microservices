package com.microservices.bankapp.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long customerId;
    private String name;
    private String gender;
    private Integer age;
    private String identification;
    private String address;
    private String phone;
    private String password;
    private Boolean status;
}
