package com.swiftcart.swiftcartcustomerservice.repositories;

import com.swiftcart.swiftcartcustomerservice.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> { // By writing Customer, Long i am telling to spring data jpa i want this repository to perform db operations for my customer entity and it. and additionally i get all the methods like save() because i extends jpaRepo.

    boolean existsByEmail(String email);

}
