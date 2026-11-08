package com.swiftcart.swiftcartorderservice.mappers;

import com.swiftcart.swiftcartorderservice.dtos.OrderRequestDTO;
import com.swiftcart.swiftcartorderservice.dtos.OrderResponseDTO;
import com.swiftcart.swiftcartorderservice.dtos.OrderUpdateDTO;
import com.swiftcart.swiftcartorderservice.models.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order toEntity(OrderRequestDTO orderRequestDTO){
        if(orderRequestDTO == null){
            return null;
        }

        Order order = new Order();
        order.setCustomerId(orderRequestDTO.getCustomerId());
        order.setProductId(orderRequestDTO.getProductId());
        order.setOrderQuantity(orderRequestDTO.getOrderQuantity());

        return order;
    }


    public OrderResponseDTO toResponseDto(Order order){
        if(order == null){
            return null;
        }
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(order.getId());
        orderResponseDTO.setCustomerId(order.getCustomerId());
        orderResponseDTO.setProductId(order.getProductId());
        orderResponseDTO.setProductName(order.getProductName());
        orderResponseDTO.setOrderQuantity(order.getOrderQuantity());
        orderResponseDTO.setUnitPrice(order.getUnitPrice());
        orderResponseDTO.setTotalPrice(order.getTotalPrice());
        orderResponseDTO.setOrderStatus(order.getOrderStatus());
        orderResponseDTO.setCreatedAt(order.getCreatedAt());
        orderResponseDTO.setUpdatedAt(order.getUpdatedAt());


        return orderResponseDTO;
    }

    public void updateEntity(OrderUpdateDTO orderUpdateDTO, Order order){
        order.setOrderQuantity(orderUpdateDTO.getOrderQuantity());
    }
}
