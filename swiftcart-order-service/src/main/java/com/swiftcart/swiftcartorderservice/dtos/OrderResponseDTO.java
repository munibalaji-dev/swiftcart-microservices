package com.swiftcart.swiftcartorderservice.dtos;

import com.swiftcart.swiftcartorderservice.models.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderResponseDTO {

    @Schema(description = "Unique order ID", example = "101")
    private Long id;

    @Schema(description = "Customer ID", example = "1")
    private Long customerId;

    @Schema(description = "Product ID",
            example = "1")
    private Long productId;

    @Schema(description = "Product name at order time",
            example = "Samsung Galaxy S24")
    private String productName;

    @Schema(description = "Product Stock Quantity", example = "10")
    private Integer orderQuantity;

    @Schema(description = "Price of one product",
            example = "75000.00")
    private BigDecimal unitPrice;

    @Schema(description = "Total order amount",
            example = "150000.00")
    private BigDecimal totalPrice;

    @Schema(description = "Order status", examples = "PLACED, DELIVERED, CANCELLED")
    private OrderStatus orderStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
