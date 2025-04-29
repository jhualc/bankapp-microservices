package com.microservices.bankapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.bankapp.dto.TransactionCreateDTO;
import com.microservices.bankapp.dto.TransactionDTO;
import com.microservices.bankapp.entity.Transaction;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.microservices.bankapp.enums.TransactionType;
import java.time.LocalDateTime;


@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllTransactions() throws Exception {
        TransactionDTO dto = new TransactionDTO();
        dto.setTransactionId(1L);
        dto.setAmount(100.0);
        dto.setType(TransactionType.DEPOSIT);

        Mockito.when(transactionService.getAllTransactions()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].type").value("DEPOSIT"))
                .andExpect(jsonPath("$[0].amount").value(100.0));
    }

    @Test
    void shouldCreateTransaction() throws Exception {
        TransactionCreateDTO createDTO = new TransactionCreateDTO();
        createDTO.setAccountId(1L);
        createDTO.setType(TransactionType.WITHDRAWAL);
        createDTO.setAmount(50.0);

        TransactionDTO responseDTO = new TransactionDTO();
        responseDTO.setTransactionId(10L);
        responseDTO.setType(TransactionType.WITHDRAWAL);
        responseDTO.setAmount(50.0);

        Mockito.when(transactionService.createTransaction(any(TransactionCreateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(10L))
                .andExpect(jsonPath("$.type").value("WITHDRAWAL"));
    }

    @Test
    void shouldDeleteTransaction() throws Exception {
        Mockito.doNothing().when(transactionService).deleteTransaction(5L);

        mockMvc.perform(delete("/api/transactions/5"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetTransactionsByCustomerIdAndDateRange() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1L);
        transaction.setAmount(300.0);
        transaction.setDate(LocalDateTime.of(2024, 1, 1, 0, 0));

        Mockito.when(transactionService.getTransactionsByCustomerIdAndDateRange(eq(1L), any(), any()))
                .thenReturn(List.of(transaction));

        mockMvc.perform(get("/api/transactions/report")
                        .param("customerId", "1")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-12-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].amount").value(300.0));
    }
}
