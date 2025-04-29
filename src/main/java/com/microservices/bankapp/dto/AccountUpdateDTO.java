package com.microservices.bankapp.dto;

import lombok.Data;

@Data
public class AccountUpdateDTO {
    private String accountNumber;
    private String accountType;
    private Double initialBalance;
    private Boolean status;
    private Long customerId;
}
