package com.dvd.rental.dvd_rental_system.services;

import com.dvd.rental.dvd_rental_system.dto.RentalDTO;
import com.dvd.rental.dvd_rental_system.models.Customer;
import com.dvd.rental.dvd_rental_system.models.Film;
import com.dvd.rental.dvd_rental_system.models.Rental;
import com.dvd.rental.dvd_rental_system.repositories.CustomerRepository;
import com.dvd.rental.dvd_rental_system.repositories.FilmRepository;
import com.dvd.rental.dvd_rental_system.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;



import com.dvd.rental.dvd_rental_system.dto.FilmDTO;


@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private FilmService filmService;

    private static final double LATE_FEE_PER_DAY = 0.50;

    public RentalDTO createRental(RentalDTO dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Film film = filmRepository.findById(dto.getFilmId())
                .orElseThrow(() -> new RuntimeException("Film not found"));

        if (!film.isAvailable()) {
            throw new RuntimeException("Film is not available");
        }

        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setFilm(film);
        rental.setRentalDate(LocalDateTime.now());
        rental.setDueDate(rental.getRentalDate().plusDays(film.getRentalDurationDays()));
        rental = rentalRepository.save(rental);

        film.setAvailable(false);
        filmRepository.save(film);

        dto.setId(rental.getId());
        dto.setRentalDate(rental.getRentalDate());
        dto.setDueDate(rental.getDueDate());
        return dto;
    }

    public RentalDTO returnFilm(UUID rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        if (rental.getReturnDate() != null) {
            throw new RuntimeException("Film already returned");
        }

        rental.setReturnDate(LocalDateTime.now());
        double lateFee = calculateLateFee(rental);
        rental.setLateFee(lateFee);
        rental.getCustomer().setLateFees(rental.getCustomer().getLateFees() + lateFee);

        rental.getFilm().setAvailable(true);
        rentalRepository.save(rental);
        customerRepository.save(rental.getCustomer());
        filmRepository.save(rental.getFilm());

        return toDTO(rental);
    }

    public List<RentalDTO> getCustomerRentals(UUID customerId) {
        return rentalRepository.findByCustomerId(customerId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<FilmDTO> recommendFilms(UUID customerId) {
        List<Rental> rentals = rentalRepository.findByCustomerId(customerId);
        if (rentals.isEmpty()) return List.of();

        // Simple recommendation: films from the most rented category
        UUID topCategoryId = rentals.stream()
                .map(r -> r.getFilm().getCategory().getId())
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        return filmService.searchFilms(null, topCategoryId, null, null).stream()
                .filter(FilmDTO::isAvailable)
                .limit(5) // Top 5 available films
                .collect(Collectors.toList());
    }

    private double calculateLateFee(Rental rental) {
        if (rental.getReturnDate() == null || rental.getDueDate().isAfter(rental.getReturnDate())) {
            return 0.0;
        }
        long daysLate = ChronoUnit.DAYS.between(rental.getDueDate(), rental.getReturnDate());
        return daysLate * LATE_FEE_PER_DAY;
    }

    private RentalDTO toDTO(Rental rental) {
        RentalDTO dto = new RentalDTO();
        dto.setId(rental.getId());
        dto.setCustomerId(rental.getCustomer().getId());
        dto.setFilmId(rental.getFilm().getId());
        dto.setRentalDate(rental.getRentalDate());
        dto.setDueDate(rental.getDueDate());
        dto.setReturnDate(rental.getReturnDate());
        dto.setLateFee(rental.getLateFee());
        return dto;
    }
}