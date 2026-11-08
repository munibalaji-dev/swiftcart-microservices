package com.swiftcart.swiftcartorderservice.controllers;

import com.swiftcart.swiftcartorderservice.dtos.OrderRequestDTO;
import com.swiftcart.swiftcartorderservice.dtos.OrderResponseDTO;
import com.swiftcart.swiftcartorderservice.dtos.OrderStatusUpdateDTO;
import com.swiftcart.swiftcartorderservice.dtos.OrderUpdateDTO;
import com.swiftcart.swiftcartorderservice.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ORDER SERVICE APIs")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;


    @Operation(description = "To create order")
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO){
        return new ResponseEntity<>(orderService.createOrder(orderRequestDTO), HttpStatus.CREATED);
    }


    @Operation(description = "To get an order using its id")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id){
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(orderService.getOrderById(id));
    }

    @Operation(description = "To retrieve all orders")
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(){
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }


    @Operation(description = "To Update Order only order quantity and that to when orderStatus is placed")
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrderById(@Valid @PathVariable Long id, @Valid @RequestBody OrderUpdateDTO orderUpdateDTO){
        return new ResponseEntity<>(orderService.updateOrderById(id, orderUpdateDTO), HttpStatus.OK);
    }


    @Operation(description = "To cancel order using order id")
    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.cancelOrder(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(@PathVariable Long id, @Valid @RequestBody OrderStatusUpdateDTO updateDTO) {

        return ResponseEntity.ok(
                orderService.updateOrderStatus(id, updateDTO)
        );
    }

}
