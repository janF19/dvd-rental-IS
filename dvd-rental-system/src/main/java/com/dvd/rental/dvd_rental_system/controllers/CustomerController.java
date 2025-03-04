package com.dvd.rental.dvd_rental_system.controllers;


import com.dvd.rental.dvd_rental_system.dto.CustomerDTO;
import com.dvd.rental.dvd_rental_system.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public CustomerDTO createCustomer(@RequestBody CustomerDTO dto) {
        return customerService.createCustomer(dto);
    }

    @GetMapping("/search")
    public List<CustomerDTO> searchCustomers(@RequestParam String query) {
        return customerService.searchCustomers(query);
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable UUID id) {
        return customerService.getCustomerById(id);
    }
}