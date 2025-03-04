package com.dvd.rental.dvd_rental_system.repositories;

import com.dvd.rental.dvd_rental_system.models.Actor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActorRepository extends JpaRepository<Actor, UUID> {
    Actor findByName(String name);
}