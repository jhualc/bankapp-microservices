package com.microservices.bankapp.repository;

import com.microservices.bankapp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountCustomerIdAndDateBetween(Long customerId, LocalDateTime from, LocalDateTime to);
    Optional<Transaction> findTopByAccountAccountIdOrderByDateDesc(Long accountId);


}
