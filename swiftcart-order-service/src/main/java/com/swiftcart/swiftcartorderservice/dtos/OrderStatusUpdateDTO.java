package com.swiftcart.swiftcartorderservice.dtos;


import com.swiftcart.swiftcartorderservice.models.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusUpdateDTO {

    @NotNull(message = "The new status must be provided.")
    private OrderStatus orderStatus;
}
