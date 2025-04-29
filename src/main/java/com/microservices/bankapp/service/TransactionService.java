package com.microservices.bankapp.service;

import com.microservices.bankapp.dto.TransactionCreateDTO;
import com.microservices.bankapp.dto.TransactionDTO;
import com.microservices.bankapp.dto.TransactionRequestDTO;
import com.microservices.bankapp.entity.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionService {
    List<TransactionDTO> getAllTransactions();
    TransactionDTO createTransaction(TransactionCreateDTO dto);
    void deleteTransaction(Long id);
    List<Transaction> getTransactionsByCustomerIdAndDateRange(Long customerId, LocalDate startDate, LocalDate endDate);
}