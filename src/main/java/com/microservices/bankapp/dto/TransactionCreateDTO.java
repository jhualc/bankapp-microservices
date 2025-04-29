package com.microservices.bankapp.dto;

import lombok.Data;

@Data
public class TransactionCreateDTO {
    private String transactionType; // "DEPOSIT" o "WITHDRAWAL"
    private Double amount;
    private Long accountId;
}
