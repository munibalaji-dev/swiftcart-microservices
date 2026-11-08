package com.swiftcart.swiftcartorderservice.client;

import com.swiftcart.swiftcartorderservice.dtos.CustomerResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="swiftcart-customer-service", url= "${customer.service.url}")
public interface CustomerClient {

    @GetMapping("/customers/{id}")
    CustomerResponseDTO getCustomerById(@PathVariable Long id);
}
