package com.dvd.rental.dvd_rental_system.repositories;

import com.dvd.rental.dvd_rental_system.models.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FilmRepository extends JpaRepository<Film, UUID> {
    List<Film> findByTitleContainingIgnoreCase(String title);
    List<Film> findByCategoryId(UUID categoryId);
    List<Film> findByActorsId(UUID actorId);
    List<Film> findByDescriptionContainingIgnoreCase(String description);
}