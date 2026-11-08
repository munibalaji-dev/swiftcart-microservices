package com.swiftcart.swiftcartorderservice.services;

import com.swiftcart.swiftcartorderservice.dtos.OrderRequestDTO;
import com.swiftcart.swiftcartorderservice.dtos.OrderResponseDTO;
import com.swiftcart.swiftcartorderservice.dtos.OrderStatusUpdateDTO;
import com.swiftcart.swiftcartorderservice.dtos.OrderUpdateDTO;
import com.swiftcart.swiftcartorderservice.models.OrderStatus;

import java.util.List;

public interface OrderService {


    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    OrderResponseDTO getOrderById(Long id);

    List<OrderResponseDTO> getAllOrders();

    OrderResponseDTO updateOrderById(Long id, OrderUpdateDTO orderUpdateDTO);

    String cancelOrder(Long id);

    OrderResponseDTO updateOrderStatus(Long id, OrderStatusUpdateDTO orderStatusUpdateDTO);
}
