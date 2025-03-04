package com.dvd.rental.dvd_rental_system.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryDTO {
    private UUID id;
    private String name;
}