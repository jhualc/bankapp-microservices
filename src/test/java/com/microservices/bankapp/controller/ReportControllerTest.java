package com.microservices.bankapp.controller;

import com.microservices.bankapp.dto.TransactionReportDTO;
import com.microservices.bankapp.entity.Account;
import com.microservices.bankapp.entity.Customer;
import com.microservices.bankapp.entity.Transaction;
import com.microservices.bankapp.service.ReportService;
import com.microservices.bankapp.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.microservices.bankapp.enums.AccountType;
import java.time.LocalDateTime;



@WebMvcTest(ReportController.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private ReportService reportService;

    @Test
    void shouldReturnTransactionReportDTOList() throws Exception {
        // Simular entidades necesarias
        Customer customer = new Customer();
        customer.setName("Carlos Ruiz");

        Account account = new Account();
        account.setAccountNumber("123456");
        account.setAccountType(AccountType.SAVINGS);
        account.setStatus(true);
        account.setCustomer(customer);

        Transaction transaction = new Transaction();
        transaction.setDate(LocalDateTime.of(2024, 1, 1, 0, 0));
        transaction.setAmount(200.0);
        transaction.setInitialBalance(1000.0);
        transaction.setAvailableBalance(1200.0);
        transaction.setAccount(account);

        Mockito.when(transactionService.getTransactionsByCustomerIdAndDateRange(
                        eq(1L), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(List.of(transaction));

        mockMvc.perform(get("/api/reports/transactions")
                        .param("customerId", "1")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-12-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerName").value("Carlos Ruiz"))
                .andExpect(jsonPath("$[0].accountNumber").value("123456"))
                .andExpect(jsonPath("$[0].movement").value("200.0"));
    }

    @Test
    void shouldReturnPdfBytes() throws Exception {
        byte[] fakePdf = "PDF-CONTENT".getBytes();

        Mockito.when(reportService.generateReportPdf(eq(1L), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(fakePdf);

        mockMvc.perform(get("/api/reports/transactions/pdf")
                        .param("customerId", "1")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-12-31"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=reporte.pdf"))
                .andExpect(header().string("Content-Type", "application/pdf"))
                .andExpect(content().bytes(fakePdf));
    }
}
