package com.microservices.bankapp.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private Long transactionId;
    private LocalDateTime date;
    private String transactionType;
    private Double amount;
    private Double availableBalance;
    private Long accountId;
    private String accountNumber;
    private String accountType;
    private Double initialBalance;
    private Boolean status;
}
