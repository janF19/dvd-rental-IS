package com.dvd.rental.dvd_rental_system.repositories;


import com.dvd.rental.dvd_rental_system.models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.List;
import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, UUID> {
    List<Rental> findByCustomerId(UUID customerId);
    List<Rental> findByReturnDateIsNull(); // Active rentals
}