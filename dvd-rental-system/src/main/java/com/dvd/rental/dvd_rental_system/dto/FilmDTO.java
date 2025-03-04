package com.dvd.rental.dvd_rental_system.dto;



import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class FilmDTO {
    private UUID id;
    private String title;
    private String description;
    private UUID categoryId;
    private String categoryName;
    private List<UUID> actorIds;
    private List<String> actorNames;
    private boolean isAvailable;
    private int rentalDurationDays;
}