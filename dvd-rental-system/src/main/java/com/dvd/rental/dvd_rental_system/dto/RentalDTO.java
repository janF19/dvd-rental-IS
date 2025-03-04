package com.dvd.rental.dvd_rental_system.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RentalDTO {
    private UUID id;
    private UUID customerId;
    private UUID filmId;
    private LocalDateTime rentalDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    private double lateFee;
}