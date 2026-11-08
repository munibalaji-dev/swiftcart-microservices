package com.swiftcart.swiftcartproductservice.services;

import com.swiftcart.swiftcartproductservice.dtos.ProductRequestDTO;
import com.swiftcart.swiftcartproductservice.dtos.ProductResponseDTO;
import com.swiftcart.swiftcartproductservice.dtos.ProductUpdateDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface ProductService {
    ProductResponseDTO addProduct(@Valid ProductRequestDTO productRequestDTO);

    ProductResponseDTO getProductById(Long id);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO updateProductById(Long id, @Valid ProductRequestDTO productRequestDTO);

    ProductResponseDTO updateProduct(Long id, @Valid ProductUpdateDTO productUpdateDTO);

    ProductResponseDTO deleteProductById(Long id);

    void reduceStock(Long id, Integer quantity);

    void restoreStock(Long id, Integer quantity);
}