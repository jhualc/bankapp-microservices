package com.microservices.bankapp.dto;

import lombok.Data;
import com.microservices.bankapp.enums.TransactionType;

@Data
public class TransactionCreateDTO {
    private TransactionType type; // "DEPOSIT" o "WITHDRAWAL"
    private Double amount;
    private Long accountId;
}
