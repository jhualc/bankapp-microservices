package com.microservices.bankapp.dto;

import lombok.Data;

@Data
public class TransactionReportDTO {
    private String date;
    private String customerName;
    private String accountNumber;
    private String accountType;
    private Double initialBalance;
    private Boolean status;
    private String movement;
    private Double availableBalance;
}
