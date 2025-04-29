package com.microservices.bankapp.repository;

import com.microservices.bankapp.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
