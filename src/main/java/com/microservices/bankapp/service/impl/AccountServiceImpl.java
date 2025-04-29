package com.microservices.bankapp.service.impl;

import com.microservices.bankapp.dto.AccountDTO;
import com.microservices.bankapp.dto.AccountUpdateDTO;
import com.microservices.bankapp.entity.Account;
import com.microservices.bankapp.entity.Customer;
import com.microservices.bankapp.enums.AccountType;
import com.microservices.bankapp.repository.AccountRepository;
import com.microservices.bankapp.repository.CustomerRepository;
import com.microservices.bankapp.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository; // 🔥 nuevo

    public AccountServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(account -> {
                    AccountDTO dto = new AccountDTO();
                    dto.setAccountId(account.getAccountId());
                    dto.setAccountNumber(account.getAccountNumber());
                    dto.setAccountType(account.getAccountType().toString());
                    dto.setInitialBalance(account.getInitialBalance());
                    dto.setStatus(account.getStatus());
                    dto.setCustomerId(account.getCustomer().getId());
                    dto.setCustomerName(account.getCustomer().getName());
                    return dto;
                })
                .collect(Collectors.toList());
    }



    @Override
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account createAccount(Account account, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerId));
        account.setCustomer(customer);
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Long accountId, AccountUpdateDTO accountUpdateDTO) {
        Account existingAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        existingAccount.setAccountNumber(accountUpdateDTO.getAccountNumber());
        existingAccount.setAccountType(AccountType.valueOf(accountUpdateDTO.getAccountType())); // 🔥 conversión aquí
        existingAccount.setInitialBalance(accountUpdateDTO.getInitialBalance());
        existingAccount.setStatus(accountUpdateDTO.getStatus());

        if (accountUpdateDTO.getCustomerId() != null) {
            Customer customer = customerRepository.findById(accountUpdateDTO.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            existingAccount.setCustomer(customer);
        }

        return accountRepository.save(existingAccount);
    }



    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
