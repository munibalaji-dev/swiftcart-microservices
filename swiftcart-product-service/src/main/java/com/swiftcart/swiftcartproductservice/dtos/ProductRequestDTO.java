package com.swiftcart.swiftcartproductservice.dtos;

import com.swiftcart.swiftcartproductservice.models.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;



@Getter
@Setter
public class ProductRequestDTO {

    @NotBlank(message = "Product name is required")
    @Schema(name = "Product Name")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Schema(name = "Product Description")
    private String description;

    @NotBlank(message = "Category is required")
    @Schema(name = "Product Category")
    private String category;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than zero")
    @Schema(name = "Product Price")
    private BigDecimal price;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    @Schema(name = "Product StockQuantity")
    private Integer stockQuantity;
}
