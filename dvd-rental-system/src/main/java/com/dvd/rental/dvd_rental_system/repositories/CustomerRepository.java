package com.dvd.rental.dvd_rental_system.repositories;



import com.dvd.rental.dvd_rental_system.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    List<Customer> findByNameContainingIgnoreCase(String name);
    Customer findByEmail(String email);
}

//provides jpa all JpaRepository provides methods like save(), findById(), findAll(), etc.
//No custom queries needed for basic CRUD.