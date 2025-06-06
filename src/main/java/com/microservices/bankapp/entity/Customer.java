package com.microservices.bankapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "customer_id")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer extends Person {

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean status;
}
