package com.microservices.bankapp.controller;

import com.microservices.bankapp.dto.TransactionCreateDTO;
import com.microservices.bankapp.dto.TransactionDTO;
import com.microservices.bankapp.dto.TransactionRequestDTO;
import com.microservices.bankapp.entity.Transaction;
import com.microservices.bankapp.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }


    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionCreateDTO dto) {
        TransactionDTO created = transactionService.createTransaction(dto);
        return ResponseEntity.ok(created);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/report")
    public ResponseEntity<List<Transaction>> getTransactionsByCustomerIdAndDateRange(
            @RequestParam Long customerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<Transaction> transactions = transactionService.getTransactionsByCustomerIdAndDateRange(customerId, startDate, endDate);
        return ResponseEntity.ok(transactions);
    }
}
