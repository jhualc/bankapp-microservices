package com.microservices.bankapp.service;

import com.microservices.bankapp.dto.AccountDTO;
import com.microservices.bankapp.dto.AccountUpdateDTO;
import com.microservices.bankapp.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<AccountDTO> getAllAccounts();

    Optional<Account> getAccountById(Long id);

    Account createAccount(Account account, Long customerId);

    Account updateAccount(Long accountId, AccountUpdateDTO accountUpdateDTO);

    void deleteAccount(Long id);
}
