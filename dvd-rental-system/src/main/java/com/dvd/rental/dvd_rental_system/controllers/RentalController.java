package com.dvd.rental.dvd_rental_system.controllers;


import com.dvd.rental.dvd_rental_system.dto.FilmDTO;
import com.dvd.rental.dvd_rental_system.dto.RentalDTO;
import com.dvd.rental.dvd_rental_system.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.UUID;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @PostMapping
    public RentalDTO createRental(@RequestBody RentalDTO dto) {
        return rentalService.createRental(dto);
    }

    @PostMapping("/{id}/return")
    public RentalDTO returnFilm(@PathVariable UUID id) {
        return rentalService.returnFilm(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<RentalDTO> getCustomerRentals(@PathVariable UUID customerId) {
        return rentalService.getCustomerRentals(customerId);
    }

    @GetMapping("/recommendations/{customerId}")
    public List<FilmDTO> getRecommendations(@PathVariable UUID customerId) {
        return rentalService.recommendFilms(customerId);
    }
}
