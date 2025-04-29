package com.microservices.bankapp.repository;

import com.microservices.bankapp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // 👇 ESTE ES EL QUE FALTA
    Optional<Account> findByAccountNumber(String accountNumber);
}
