package com.microservices.bankapp.controller;

import com.microservices.bankapp.dto.AccountDTO;
import com.microservices.bankapp.dto.AccountRequestDTO;
import com.microservices.bankapp.dto.AccountUpdateDTO;
import com.microservices.bankapp.entity.Account;
import com.microservices.bankapp.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountDTO> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody AccountRequestDTO request) {
        Account account = new Account();
        account.setAccountNumber(request.getAccountNumber());
        account.setAccountType(request.getAccountType());
        account.setInitialBalance(request.getInitialBalance());
        account.setStatus(request.getStatus());

        Account createdAccount = accountService.createAccount(account, request.getCustomerId());
        return ResponseEntity.ok(createdAccount);
    }

    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable Long id, @RequestBody AccountUpdateDTO accountUpdateDTO) {
        return accountService.updateAccount(id, accountUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
