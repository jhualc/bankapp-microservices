package com.microservices.bankapp.dto;

import com.microservices.bankapp.enums.TransactionType;
import lombok.Data;

@Data
public class TransactionRequestDTO {
    private String accountNumber;
    private TransactionType transactionType;
    private Double amount;
}
