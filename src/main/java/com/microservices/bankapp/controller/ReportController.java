package com.microservices.bankapp.controller;

import com.microservices.bankapp.dto.TransactionReportDTO;
import com.microservices.bankapp.entity.Transaction;
import com.microservices.bankapp.service.ReportService;
import com.microservices.bankapp.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import java.util.Base64;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final TransactionService transactionService;
    private final ReportService reportService;

    public ReportController(TransactionService transactionService, ReportService reportService) {
        this.transactionService = transactionService;
        this.reportService = reportService;
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionReportDTO>> getTransactions(
            @RequestParam Long customerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<Transaction> transactions = transactionService.getTransactionsByCustomerIdAndDateRange(customerId, startDate, endDate);

        List<TransactionReportDTO> report = transactions.stream().map(transaction -> {
            TransactionReportDTO dto = new TransactionReportDTO();
            dto.setDate(String.valueOf(transaction.getDate()));
            dto.setCustomerName(transaction.getAccount().getCustomer().getName());
            dto.setAccountNumber(transaction.getAccount().getAccountNumber());
            dto.setAccountType(transaction.getAccount().getAccountType().toString());
            dto.setInitialBalance(transaction.getInitialBalance());
            dto.setStatus(transaction.getAccount().getStatus());
            dto.setMovement(String.valueOf(transaction.getAmount()));
            dto.setAvailableBalance(transaction.getAvailableBalance());
            return dto;
        }).toList();

        return ResponseEntity.ok(report);
    }


    @GetMapping("/transactions/pdf")
    public ResponseEntity<byte[]> getTransactionsPdf(
            @RequestParam Long customerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        byte[] pdfBytes = reportService.generateReportPdf(customerId, startDate, endDate);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=reporte.pdf")
                .header("Content-Type", "application/pdf")
                .body(pdfBytes);
    }

}
