package com.dvd.rental.dvd_rental_system.services;



import com.dvd.rental.dvd_rental_system.dto.CustomerDTO;
import com.dvd.rental.dvd_rental_system.models.Customer;
import com.dvd.rental.dvd_rental_system.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import com.dvd.rental.dvd_rental_system.models.Rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public CustomerDTO createCustomer(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer = customerRepository.save(customer);
        dto.setId(customer.getId());
        return dto;
    }

    public List<CustomerDTO> searchCustomers(String query) {
        return customerRepository.findByNameContainingIgnoreCase(query)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(UUID id) {
        return customerRepository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    private CustomerDTO toDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setLateFees(customer.getLateFees());
        dto.setRentalIds(customer.getRentals().stream().map(Rental::getId).collect(Collectors.toList()));
        return dto;
    }
}