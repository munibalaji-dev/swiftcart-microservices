package com.swiftcart.swiftcartcustomerservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)  // It means the email column cannot contain a NULL value.
    private String email;


    private String phoneNumber;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

//    private String password  Not needed without login functionality so i removed it
}
