package com.microservices.bankapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.bankapp.dto.AccountDTO;
import com.microservices.bankapp.dto.AccountRequestDTO;
import com.microservices.bankapp.dto.AccountUpdateDTO;
import com.microservices.bankapp.entity.Account;
import com.microservices.bankapp.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.microservices.bankapp.enums.AccountType;



@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllAccounts() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountId(1L);
        accountDTO.setAccountNumber("123456");
        accountDTO.setInitialBalance(1000.0);

        Mockito.when(accountService.getAllAccounts()).thenReturn(List.of(accountDTO));

        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].accountNumber").value("123456"));
    }

    @Test
    void shouldGetAccountById() throws Exception {
        Account account = new Account();
        account.setAccountId(1L);
        account.setAccountNumber("789012");

        Mockito.when(accountService.getAccountById(1L)).thenReturn(Optional.of(account));

        mockMvc.perform(get("/api/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("789012"));
    }

    @Test
    void shouldCreateAccount() throws Exception {
        AccountRequestDTO requestDTO = new AccountRequestDTO();
        requestDTO.setCustomerId(1L);
        requestDTO.setAccountNumber("555666");
        requestDTO.setAccountType(AccountType.SAVINGS);
        requestDTO.setInitialBalance(500.0);
        requestDTO.setStatus(true);

        Account createdAccount = new Account();
        createdAccount.setAccountId(10L);
        createdAccount.setAccountNumber("555666");
        createdAccount.setAccountType(AccountType.SAVINGS);
        createdAccount.setInitialBalance(500.0);
        createdAccount.setStatus(true);

        Mockito.when(accountService.createAccount(any(Account.class), eq(1L)))
                .thenReturn(createdAccount);

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(10L))
                .andExpect(jsonPath("$.accountNumber").value("555666"));
    }

    @Test
    void shouldUpdateAccount() throws Exception {
        AccountUpdateDTO updateDTO = new AccountUpdateDTO();
        updateDTO.setStatus(false);

        Account updatedAccount = new Account();
        updatedAccount.setAccountId(5L);
        updatedAccount.setStatus(false);

        Mockito.when(accountService.updateAccount(eq(5L), any(AccountUpdateDTO.class)))
                .thenReturn(updatedAccount);

        mockMvc.perform(put("/api/accounts/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(5L))
                .andExpect(jsonPath("$.status").value(false));
    }

    @Test
    void shouldDeleteAccount() throws Exception {
        Mockito.doNothing().when(accountService).deleteAccount(3L);

        mockMvc.perform(delete("/api/accounts/3"))
                .andExpect(status().isNoContent());
    }
}
