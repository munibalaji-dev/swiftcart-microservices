package com.swiftcart.swiftcartorderservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductResponseDTO {

    @Schema(name = "Product Unique ID")
    private Long id;

    @Schema(name = "Product Name")
    private String name;

    @Schema(name = "Product Description")
    private String description;

    @Schema(name = "Which category this product contains")
    private String category;

    @Schema(name = "Price of a product")
    private BigDecimal price;

    @Schema(name = "It tells how much stock is available")
    private Integer stockQuantity;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}