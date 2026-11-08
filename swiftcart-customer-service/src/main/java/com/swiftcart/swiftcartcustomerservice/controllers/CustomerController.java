package com.swiftcart.swiftcartcustomerservice.controllers;

import com.swiftcart.swiftcartcustomerservice.dtos.CustomerRequestDTO;
import com.swiftcart.swiftcartcustomerservice.dtos.CustomerResponseDTO;
import com.swiftcart.swiftcartcustomerservice.dtos.CustomerUpdateDTO;
import com.swiftcart.swiftcartcustomerservice.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="CUSTOMER SERVICE APIs")
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){  // If we're using the single constructor @Autowired annotation is not required because spring will automatically detects it and injects the bean.
        this.customerService = customerService;
    }

    @Operation(summary = "Register new customer")
    @PostMapping
    public ResponseEntity<CustomerResponseDTO>  registerCustomer(@Valid @RequestBody CustomerRequestDTO customerRequestDTO){  // Spring checks the DTO validation annotations before executing the controller method, because of @Valid.It triggers the validation rules that we have defined inside my DTO.
        return new ResponseEntity<>(customerService.registerCustomer(customerRequestDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve customer using their id")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id){
        return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve all customers")
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers(){
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @Operation(summary = "Update entire customer details")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomerById(@PathVariable Long id, @Valid @RequestBody CustomerRequestDTO customerRequestDTO){
        return new ResponseEntity<>(customerService.updateCustomerById(id, customerRequestDTO), HttpStatus.OK);
    }

    @Operation(summary = "Partially update customer details")
    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerUpdateDTO customerUpdateDTO
    ){
        return new ResponseEntity<>(customerService.updateCustomer(id, customerUpdateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Delete customer using their id")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> deleteCustomer(@PathVariable Long id){
        return new ResponseEntity<>(customerService.deleteCustomer(id), HttpStatus.OK);
    }
}
