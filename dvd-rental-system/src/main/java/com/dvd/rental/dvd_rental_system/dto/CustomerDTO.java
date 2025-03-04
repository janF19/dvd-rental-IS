package com.dvd.rental.dvd_rental_system.dto;


import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CustomerDTO {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private double lateFees;
    private List<UUID> rentalIds;
}