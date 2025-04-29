package com.microservices.bankapp.dto;

import com.microservices.bankapp.enums.AccountType;
import lombok.Data;

@Data
public class AccountRequestDTO {
    private String accountNumber;
    private AccountType accountType;
    private Double initialBalance;
    private Boolean status;
    private Long customerId;
}
