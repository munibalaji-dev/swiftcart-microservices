package com.swiftcart.swiftcartcustomerservice.services;

import com.swiftcart.swiftcartcustomerservice.dtos.CustomerRequestDTO;
import com.swiftcart.swiftcartcustomerservice.dtos.CustomerResponseDTO;
import com.swiftcart.swiftcartcustomerservice.dtos.CustomerUpdateDTO;
import com.swiftcart.swiftcartcustomerservice.exceptions.CustomerNotFoundException;
import com.swiftcart.swiftcartcustomerservice.mappers.CustomerMapper;
import com.swiftcart.swiftcartcustomerservice.models.Customer;
import com.swiftcart.swiftcartcustomerservice.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor  // @RequiredArgsConstructor is useful for Spring dependency injection. it includes only the final fields.
public class CustomerServiceImplementation implements CustomerService{

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponseDTO registerCustomer(CustomerRequestDTO customerRequestDTO) {


        if(customerRepository.existsByEmail(customerRequestDTO.getEmail())){
            throw new CustomerNotFoundException("Customer already exists");
        }

        Customer customer = customerMapper.toEntity(customerRequestDTO);
        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.toResponseDTO(savedCustomer);
    }

    @Override
    public CustomerResponseDTO getCustomerById(Long id) {

        Optional<Customer> byId = customerRepository.findById(id); // It returns an Optional<Customer> instead of directly returning a Customer. The result is stored in the variable byId.If byId contains a customer.
        Customer customer = byId.orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + id));

        return customerMapper.toResponseDTO(customer);
    }


    @Override
    public List<CustomerResponseDTO> getAllCustomers(){

        List<Customer> customerList = customerRepository.findAll();

        List<CustomerResponseDTO> customerResponseDTOList = new ArrayList<>();


        for(Customer customer : customerList){
            CustomerResponseDTO responseDTO = customerMapper.toResponseDTO(customer);
            customerResponseDTOList.add(responseDTO);
        }

        return customerResponseDTOList;

//        return customerList.stream().map(customerMapper::toResponseDTO).toList();
    }

    @Override
    public CustomerResponseDTO updateCustomerById(Long id, CustomerRequestDTO customerRequestDTO) {

        Optional<Customer> optionalCustomer = customerRepository.findById(id);

//        if(optionalCustomer.isPresent()){
//            Customer customer = optionalCustomer.get(); // get() will give customer
//
//            customer.setName(customerRequestDTO.getName());
//            customer.setEmail(customerRequestDTO.getEmail());
//            customer.setPhoneNumber(customerRequestDTO.getPhoneNumber());
//
//            LocalDateTime localDateTime = LocalDateTime.now();
//
//            customer.setUpdatedAt(localDateTime);
//        }
//        else{
//            System.out.println("Customer not found");
//        }


        Customer customer = optionalCustomer.orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + id + " to update"));// Because orElseThrow() expects a Supplier of an exception.         In simple terms, a Supplier is something that provides a value when requested.


        customer.setName(customerRequestDTO.getName());
        customer.setEmail(customerRequestDTO.getEmail());
        customer.setPhoneNumber(customerRequestDTO.getPhoneNumber());

        LocalDateTime localDateTime = LocalDateTime.now();

        customer.setUpdatedAt(localDateTime);

        Customer save = customerRepository.save(customer);

        return customerMapper.toResponseDTO(save);
    }

    @Override
    public CustomerResponseDTO updateCustomer(Long id, CustomerUpdateDTO customerUpdateDTO) {


        Optional<Customer> byId = customerRepository.findById(id);

        Customer customer = byId.orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + id + " to update partially"));

        if(customerUpdateDTO.getName() != null){
            customer.setName(customerUpdateDTO.getName());
        }
        if (customerUpdateDTO.getEmail() != null) {
            customer.setEmail(customerUpdateDTO.getEmail());
        }
        if (customerUpdateDTO.getPhoneNumber() != null) {
            customer.setPhoneNumber(customerUpdateDTO.getPhoneNumber());
        }


        Customer save = customerRepository.save(customer);

        return customerMapper.toResponseDTO(save);
    }

    @Override
    public CustomerResponseDTO deleteCustomer(Long id) {

        // Why optional is if a customer will find it returns, if not found it returns null so optional doesnt take null it behaves like Optional.empty(). so optional is empty() not null
        Optional<Customer> byId = customerRepository.findById(id);  // It returns an Optional<Customer> instead of directly returning a Customer. orElseThrow() returns that customer and assigns it to customer. If byId is empty, it throws CustomerNotFoundException.

        Customer customer = byId.orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + id + " to delete"));

        CustomerResponseDTO customerResponseDTO = customerMapper.toResponseDTO(customer);

        customerRepository.delete(customer);

        return customerResponseDTO;
    }
}
