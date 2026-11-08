package com.swiftcart.swiftcartorderservice.services;

import com.swiftcart.swiftcartorderservice.client.CustomerClient;
import com.swiftcart.swiftcartorderservice.client.ProductClient;
import com.swiftcart.swiftcartorderservice.dtos.*;
import com.swiftcart.swiftcartorderservice.exceptions.*;
import com.swiftcart.swiftcartorderservice.mappers.OrderMapper;
import com.swiftcart.swiftcartorderservice.models.Order;
import com.swiftcart.swiftcartorderservice.models.OrderStatus;
import com.swiftcart.swiftcartorderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImplementation implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomerClient customerClient;
    private final ProductClient productClient;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {

        // 1. Verify customer exists
            CustomerResponseDTO customer = customerClient.getCustomerById(orderRequestDTO.getCustomerId());

        // 2. Retrieve Product details
        ProductResponseDTO product = productClient.getProductById(orderRequestDTO.getProductId());




        // 3. Check whether sufficient stock is available
        if(product.getStockQuantity() < orderRequestDTO.getOrderQuantity()){
            throw new InsufficientStock("Insufficient product stock");
        }

        StockUpdateDTO stockUpdateDTO = new StockUpdateDTO();
        stockUpdateDTO.setStockQuantity(orderRequestDTO.getOrderQuantity());

        productClient.reduceStock(orderRequestDTO.getProductId(), stockUpdateDTO);



        // 4. Convert RequestDTO into Order entity
        Order entity = orderMapper.toEntity(orderRequestDTO);




        // 5. Set product details
        entity.setProductName(product.getName());
        entity.setUnitPrice(product.getPrice());




        // 6. Calculate total price
        entity.setTotalPrice(product.getPrice().multiply( // The reason we I use BigDecimal.valueOf() is that BigDecimal.multiply() expects another BigDecimal, not an int or Integer. Quantity is Integer so, I converted the Integer into BigDecimal.
                BigDecimal.valueOf(orderRequestDTO.getOrderQuantity())));



        // 7. Set order status
        entity.setOrderStatus(OrderStatus.PLACED);




        // 8. Set Time Stamps

        LocalDateTime localDateTime = LocalDateTime.now();
        entity.setCreatedAt(localDateTime);
        entity.setUpdatedAt(localDateTime);


        // 9. Save order
        Order savedOrder = null;

        try {
            savedOrder = orderRepository.save(entity);
        }
        catch (Exception e){
            productClient.restoreStock(orderRequestDTO.getProductId(), stockUpdateDTO); // the same stockupdatedto we use to reduce stock again we use the same stockupdatedto which is orderquantity to restore agin in case order save fails.

            throw e;
        }
        return orderMapper.toResponseDto(savedOrder);
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {


        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id));

        return orderMapper.toResponseDto(order);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {

        List<Order> allOrders = orderRepository.findAll();

        List<OrderResponseDTO> orderResponseDTOList = new ArrayList<>();

        for(Order order : allOrders){
            OrderResponseDTO orderResponseDTO = orderMapper.toResponseDto(order);
            orderResponseDTOList.add(orderResponseDTO);
        }

        return orderResponseDTOList;
    }

    @Override
    public OrderResponseDTO updateOrderById(Long id, OrderUpdateDTO orderUpdateDTO) {

        // 1. Find existing order
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id + " to update"));


        // 2. Check order status
        if(order.getOrderStatus() != OrderStatus.PLACED){
            throw new OnlyPlacedOrdersUpdatedException("Only orders with PLACED status can be updated");
        }

        // 3. Calculate quantity difference
        int difference = orderUpdateDTO.getOrderQuantity() - order.getOrderQuantity();

        // 4. Adjust product stock
        StockUpdateDTO stockUpdateDTO = null;

        if(difference != 0){
            stockUpdateDTO = new StockUpdateDTO();

            stockUpdateDTO.setStockQuantity(Math.abs(difference));

            if(difference > 0) {
                productClient.reduceStock(order.getProductId(), stockUpdateDTO);
            }
            else{
                productClient.restoreStock(order.getProductId(), stockUpdateDTO);
            }
        }


        // 5. Update order quantity
        order.setOrderQuantity(orderUpdateDTO.getOrderQuantity());


        // 6. Recalculate total price using original unit price
        order.setTotalPrice(
                order.getUnitPrice().multiply(
                        BigDecimal.valueOf(orderUpdateDTO.getOrderQuantity())
                )
        );


        // 7. Update timestamp
        order.setUpdatedAt(LocalDateTime.now());


        // 8. Save updated order
        Order savedOrder;


        /*Understand the three scenarios
        Scenario	       |    Stock operation    |    If saving fails
        -------------------------------------------------------------------
        Quantity increases	|    Reduce stock	   | Restore stock
        Quantity decreases	|    Restore stock	   | Reduce stock
        Quantity unchanged	|    No operation	   | No compensation needed*/

        try{
            savedOrder = orderRepository.saveAndFlush(order); // Why saveAndFlush()? It asks JPA to send pending changes to the database immediately, so many persistence errors can be caught here instead of waiting until transaction commit.
        }
        catch (Exception e){
            // 9. Reverse stock adjustment if order saving fails in db
              if(difference > 0){
                  productClient.restoreStock(order.getProductId(), stockUpdateDTO);
              }
              else if(difference < 0){
                  productClient.reduceStock(order.getProductId(), stockUpdateDTO);
              }
              throw e;
        }
        // 10. Return response
        return orderMapper.toResponseDto(savedOrder);
    }

    @Override
    public String cancelOrder(Long id) {

        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id + " to delete"));

        if(order.getOrderStatus() != OrderStatus.PLACED && order.getOrderStatus() != OrderStatus.CONFIRMED){
            throw new OrderNotCancelledException("Order will not cancelled if the OrderStatus is not either PLACED or CONFIRMED");
        }

        StockUpdateDTO stockUpdateDTO = new StockUpdateDTO();
        stockUpdateDTO.setStockQuantity(order.getOrderQuantity());

        productClient.restoreStock(order.getProductId(), stockUpdateDTO);

        order.setOrderStatus(OrderStatus.CANCELLED);


        order.setUpdatedAt(LocalDateTime.now());


        orderRepository.save(order);


        return "Order Cancelled Successfully";
    }


    public OrderResponseDTO updateOrderStatus(Long id, OrderStatusUpdateDTO orderStatusUpdateDTO){

        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("order not found to change the order status id " + id));


        OrderStatus currentStatus = order.getOrderStatus();

        OrderStatus newStatus = orderStatusUpdateDTO.getOrderStatus();

        boolean isValid = false; // This implementation also prevents changing an order to its existing status. For example, PLACED → PLACED is invalid.


        /* Current status	Allowed next status
        -----------------------------------------------
                PLACED	    CONFIRMED, CANCELLED
                CONFIRMED	SHIPPED, CANCELLED
                SHIPPED	    DELIVERED
                DELIVERED	None
                CANCELLED	None
        --------------------------------------------------
I enforced these rules in the Order Service. */

        switch (currentStatus) {
            case PLACED:
                isValid = newStatus == OrderStatus.CONFIRMED ||
                        newStatus == OrderStatus.CANCELLED;
                break;

            case CONFIRMED:
                isValid = newStatus == OrderStatus.SHIPPED ||
                        newStatus == OrderStatus.CANCELLED;
                break;

            case SHIPPED:
                isValid = newStatus == OrderStatus.DELIVERED;
                break;

            case DELIVERED:
            case CANCELLED:
                isValid = false;
                break;
        }

        if(!isValid){
            throw new InvalidOrderStatusTransitionException(
                    "Cannot change order status from " + currentStatus + " to " + newStatus
            );
        }


        order.setOrderStatus(newStatus);
        order.setUpdatedAt(LocalDateTime.now());

        Order updatedOrder = orderRepository.save(order);


        return orderMapper.toResponseDto(updatedOrder);

    }

}
