package com.dvd.rental.dvd_rental_system.controllers;

import com.dvd.rental.dvd_rental_system.dto.FilmDTO;
import com.dvd.rental.dvd_rental_system.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/films")
public class FilmController {
    @Autowired
    private FilmService filmService;

    @PostMapping
    public FilmDTO createFilm(@RequestBody FilmDTO dto) {
        return filmService.createFilm(dto);
    }

    @GetMapping("/search")
    public List<FilmDTO> searchFilms(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID actorId,
            @RequestParam(required = false) String description) {
        // Validate that at least one parameter is provided
        if (title == null && categoryId == null && actorId == null && description == null) {
            return filmService.searchFilms(null, null, null, null); // Return all films
        }
        return filmService.searchFilms(title, categoryId, actorId, description);
    }
}