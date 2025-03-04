package com.dvd.rental.dvd_rental_system.dto;


import lombok.Data;

import java.util.UUID;

@Data
public class ActorDTO {
    private UUID id;
    private String name;
}