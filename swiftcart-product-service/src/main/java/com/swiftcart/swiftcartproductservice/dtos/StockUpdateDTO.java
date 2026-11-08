package com.swiftcart.swiftcartproductservice.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockUpdateDTO {

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer stockQuantity;
}
