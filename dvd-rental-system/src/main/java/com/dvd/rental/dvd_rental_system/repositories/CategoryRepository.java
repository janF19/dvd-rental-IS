package com.dvd.rental.dvd_rental_system.repositories;



import com.dvd.rental.dvd_rental_system.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}