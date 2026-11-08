package com.swiftcart.swiftcartorderservice.client;

import com.swiftcart.swiftcartorderservice.dtos.ProductResponseDTO;
import com.swiftcart.swiftcartorderservice.dtos.StockUpdateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "swiftcart-product-service", url = "${product.service.url}")
public interface ProductClient {

    @GetMapping("/products/{id}")
    ProductResponseDTO getProductById(@PathVariable Long id);

    @PutMapping("/products/{id}/reduce-stock")
    void reduceStock(
            @PathVariable("id") Long id,
            @RequestBody StockUpdateDTO requestDTO
    );

    @PutMapping("/products/{id}/restore-stock")
    void restoreStock(
            @PathVariable("id") Long id,
            @RequestBody StockUpdateDTO requestDTO
    );

}
