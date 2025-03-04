package com.dvd.rental.dvd_rental_system.models;



import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "categories")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name; // e.g., Action, Drama, Comedy
}
