package com.swiftcart.swiftcartcustomerservice.services;

import com.swiftcart.swiftcartcustomerservice.dtos.CustomerRequestDTO;
import com.swiftcart.swiftcartcustomerservice.dtos.CustomerResponseDTO;
import com.swiftcart.swiftcartcustomerservice.dtos.CustomerUpdateDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface CustomerService {

    CustomerResponseDTO registerCustomer(CustomerRequestDTO customerRequestDTO);

    CustomerResponseDTO getCustomerById(Long id);

    List<CustomerResponseDTO> getAllCustomers();

    CustomerResponseDTO updateCustomerById(Long id, @Valid CustomerRequestDTO customerRequestDTO);

    CustomerResponseDTO updateCustomer(Long id, @Valid CustomerUpdateDTO customerUpdateDTO);

    CustomerResponseDTO deleteCustomer(Long id);
}
