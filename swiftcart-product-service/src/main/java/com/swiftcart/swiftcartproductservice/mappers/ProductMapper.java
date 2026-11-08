package com.swiftcart.swiftcartproductservice.mappers;

import com.swiftcart.swiftcartproductservice.dtos.ProductRequestDTO;
import com.swiftcart.swiftcartproductservice.dtos.ProductResponseDTO;
import com.swiftcart.swiftcartproductservice.models.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDTO productRequestDTO){
        if(productRequestDTO == null){
            return null;
        }
        Product product = new Product();

        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setCategory(productRequestDTO.getCategory());
        product.setPrice(productRequestDTO.getPrice());
        product.setStockQuantity(productRequestDTO.getStockQuantity());

        LocalDateTime localDateTime = LocalDateTime.now();

        product.setCreatedAt(localDateTime);
        product.setUpdatedAt(localDateTime);

        return product;
    }


    public ProductResponseDTO toResponseDTO(Product product){
        if(product == null){
            return null;
        }
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();

        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setCategory(product.getCategory());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setStockQuantity(product.getStockQuantity());
        productResponseDTO.setCreatedAt(product.getCreatedAt());
        productResponseDTO.setUpdatedAt(product.getUpdatedAt());


        return productResponseDTO;
    }

}
