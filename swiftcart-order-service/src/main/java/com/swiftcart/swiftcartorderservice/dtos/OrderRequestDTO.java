package com.swiftcart.swiftcartorderservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDTO {

    @Schema(description = "ID of the customer placing the order",
            example = "1")
    @NotNull(message = "Customer ID is required")
    @Positive(message = "Customer ID must be positive")
    private Long customerId;


    @Schema(description = "ID of the product being ordered",
            example = "2")
    @NotNull(message = "Product ID is required")
    @Positive(message = "Product ID must be positive")
    private Long productId;

    @Schema(description = "Number of units ordered",
            example = "3")
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer orderQuantity;

    // Why don't I include price and totalPrice here the reason is:
        //Because the client should not decide the product price.
}
