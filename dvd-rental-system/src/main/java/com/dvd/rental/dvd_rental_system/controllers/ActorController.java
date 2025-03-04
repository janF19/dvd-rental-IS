package com.dvd.rental.dvd_rental_system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dvd.rental.dvd_rental_system.dto.ActorDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import com.dvd.rental.dvd_rental_system.services.ActorService;
import java.util.List;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    @Autowired
    private ActorService actorService;


    @PostMapping
    public ActorDTO createActor(@RequestBody ActorDTO dto){
        return actorService.createActor(dto);
    }

    @GetMapping
    public List<ActorDTO> getAllActors() {
        return actorService.getAllActors();
    }



    
}
