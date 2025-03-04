package com.dvd.rental.dvd_rental_system.services;



import com.dvd.rental.dvd_rental_system.dto.ActorDTO;
import com.dvd.rental.dvd_rental_system.models.Actor;
import com.dvd.rental.dvd_rental_system.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;

    public ActorDTO createActor(ActorDTO dto) {
        Actor actor = new Actor();
        actor.setName(dto.getName());
        actor = actorRepository.save(actor);
        dto.setId(actor.getId());
        return dto;
    }

    public List<ActorDTO> getAllActors() {
        return actorRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private ActorDTO toDTO(Actor actor) {
        ActorDTO dto = new ActorDTO();
        dto.setId(actor.getId());
        dto.setName(actor.getName());
        return dto;
    }
}