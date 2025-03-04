package com.dvd.rental.dvd_rental_system.models;



import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "rentals")
@Data
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @Column(nullable = false)
    private LocalDateTime rentalDate;

    @Column
    private LocalDateTime dueDate;

    @Column
    private LocalDateTime returnDate;

    @Column
    private double lateFee = 0.0; // Fee for this rental
}