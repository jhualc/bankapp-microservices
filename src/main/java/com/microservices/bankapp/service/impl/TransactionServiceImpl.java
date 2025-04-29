package com.microservices.bankapp.service.impl;

import com.microservices.bankapp.dto.TransactionCreateDTO;
import com.microservices.bankapp.dto.TransactionDTO;
import com.microservices.bankapp.dto.TransactionRequestDTO;
import com.microservices.bankapp.entity.Account;
import com.microservices.bankapp.entity.Transaction;
import com.microservices.bankapp.enums.TransactionType;
import com.microservices.bankapp.repository.AccountRepository;
import com.microservices.bankapp.repository.TransactionRepository;
import com.microservices.bankapp.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TransactionDTO createTransaction(TransactionCreateDTO dto) {
        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // Buscar el último movimiento para esa cuenta (opcionalmente ordenado por fecha descendente)
        Optional<Transaction> lastTransactionOpt = transactionRepository.findTopByAccountAccountIdOrderByDateDesc(account.getAccountId());

        double initialBalance;
        if (lastTransactionOpt.isPresent()) {
            // 🧠 Si hay transacciones previas, usamos el availableBalance anterior como saldo inicial
            initialBalance = lastTransactionOpt.get().getAvailableBalance();
        } else {
            // 🧠 Si no hay transacciones, usamos el saldo inicial de la cuenta
            initialBalance = account.getInitialBalance();
        }

        Transaction transaction = new Transaction();
        transaction.setTransactionType(dto.getType());
        transaction.setAmount(dto.getAmount());
        transaction.setDate(LocalDateTime.now());
        transaction.setAccount(account);
        transaction.setInitialBalance(initialBalance); // ✅ Copiamos el saldo disponible anterior

        double newBalance;
        if (transaction.getTransactionType() == TransactionType.WITHDRAWAL) {
            if (initialBalance < transaction.getAmount()) {
                throw new IllegalArgumentException("Saldo no disponible");
            }
            newBalance = initialBalance - transaction.getAmount();
        } else if (transaction.getTransactionType() == TransactionType.DEPOSIT) {
            newBalance = initialBalance + transaction.getAmount();
        } else {
            throw new IllegalArgumentException("Tipo de movimiento inválido");
        }

        transaction.setAvailableBalance(newBalance); // ✅ El nuevo saldo disponible

        transactionRepository.save(transaction);

        // 🧠 Actualizamos el saldo de la cuenta
        account.setInitialBalance(newBalance);
        accountRepository.save(account);

        return mapToDTO(transaction);
    }


    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public List<Transaction> getTransactionsByCustomerIdAndDateRange(Long customerId, LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findByAccountCustomerIdAndDateBetween(
                customerId,
                startDate.atStartOfDay(),
                endDate.plusDays(1).atStartOfDay()
        );

    }


    private TransactionDTO mapToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setDate(transaction.getDate());
        dto.setType(transaction.getTransactionType());
        dto.setAmount(transaction.getAmount());
        dto.setAvailableBalance(transaction.getAvailableBalance());
        dto.setAccountId(transaction.getAccount().getAccountId());
        dto.setInitialBalance(transaction.getInitialBalance());
        // ✅ Agregar los nuevos campos necesarios
        dto.setAccountNumber(transaction.getAccount().getAccountNumber());
        dto.setAccountType(transaction.getAccount().getAccountType().toString()); // O .name()
        dto.setInitialBalance(transaction.getAccount().getInitialBalance());
        dto.setStatus(transaction.getAccount().getStatus());

        return dto;
    }

}
