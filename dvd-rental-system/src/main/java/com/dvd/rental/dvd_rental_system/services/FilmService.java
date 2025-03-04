package com.dvd.rental.dvd_rental_system.services;

import com.dvd.rental.dvd_rental_system.dto.FilmDTO;
import com.dvd.rental.dvd_rental_system.models.Actor;
import com.dvd.rental.dvd_rental_system.models.Film;
import com.dvd.rental.dvd_rental_system.models.Category;
import com.dvd.rental.dvd_rental_system.repositories.ActorRepository;
import com.dvd.rental.dvd_rental_system.repositories.CategoryRepository;
import com.dvd.rental.dvd_rental_system.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;



@Service
public class FilmService {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ActorRepository actorRepository;

    public FilmDTO createFilm(FilmDTO dto) {
        Film film = new Film();
        film.setTitle(dto.getTitle());
        film.setDescription(dto.getDescription());
        film.setAvailable(dto.isAvailable());
        film.setRentalDurationDays(dto.getRentalDurationDays());

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        film.setCategory(category);

        List<Actor> actors = actorRepository.findAllById(dto.getActorIds());
        film.setActors(actors);

        film = filmRepository.save(film);
        dto.setId(film.getId());
        return dto;
    }

    public List<FilmDTO> searchFilms(String title, UUID categoryId, UUID actorId, String description) {
        if (title != null) return filmRepository.findByTitleContainingIgnoreCase(title).stream().map(this::toDTO).collect(Collectors.toList());
        if (categoryId != null) return filmRepository.findByCategoryId(categoryId).stream().map(this::toDTO).collect(Collectors.toList());
        if (actorId != null) return filmRepository.findByActorsId(actorId).stream().map(this::toDTO).collect(Collectors.toList());
        if (description != null) return filmRepository.findByDescriptionContainingIgnoreCase(description).stream().map(this::toDTO).collect(Collectors.toList());
        return filmRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private FilmDTO toDTO(Film film) {
        FilmDTO dto = new FilmDTO();
        dto.setId(film.getId());
        dto.setTitle(film.getTitle());
        dto.setDescription(film.getDescription());
        dto.setCategoryId(film.getCategory().getId());
        dto.setCategoryName(film.getCategory().getName());
        dto.setActorIds(film.getActors().stream().map(Actor::getId).collect(Collectors.toList()));
        dto.setActorNames(film.getActors().stream().map(Actor::getName).collect(Collectors.toList()));
        dto.setAvailable(film.isAvailable());
        dto.setRentalDurationDays(film.getRentalDurationDays());
        return dto;
    }
}