package com.swiftcart.swiftcartcustomerservice.mappers;

import com.swiftcart.swiftcartcustomerservice.dtos.CustomerRequestDTO;
import com.swiftcart.swiftcartcustomerservice.dtos.CustomerResponseDTO;
import com.swiftcart.swiftcartcustomerservice.models.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequestDTO customerRequestDTO){
        if(customerRequestDTO == null){
            return null;
        }

        Customer customer = new Customer();
        customer.setName(customerRequestDTO.getName());
        customer.setEmail(customerRequestDTO.getEmail());
        customer.setPhoneNumber(customerRequestDTO.getPhoneNumber());

        LocalDateTime localDateTime = LocalDateTime.now();

//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
//
//        String formattedDateTime = localDateTime.format(dateTimeFormatter);
//
//        LocalDateTime parsedDateTime = LocalDateTime.parse(formattedDateTime, dateTimeFormatter); // Here, I converting the formattedtime String into localdatetime and the second parameter dateTimeFormatter tells how the String is structured.

        customer.setCreatedAt(localDateTime);
        customer.setUpdatedAt(localDateTime);


        return customer;
    }


    public CustomerResponseDTO toResponseDTO(Customer customer){
        if(customer == null){
            return null;
        }


        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setId(customer.getId());
        customerResponseDTO.setName(customer.getName());
        customerResponseDTO.setEmail(customer.getEmail());
        customerResponseDTO.setPhoneNumber(customer.getPhoneNumber());
        customerResponseDTO.setCreatedAt(customer.getCreatedAt());
        customerResponseDTO.setUpdatedAt(customer.getUpdatedAt());

        return customerResponseDTO;
    }
}
