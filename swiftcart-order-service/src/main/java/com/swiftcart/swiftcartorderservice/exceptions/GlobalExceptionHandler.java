package com.swiftcart.swiftcartorderservice.exceptions;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientStock.class)
    public ResponseEntity<String> handleStockNotAvailableException(InsufficientStock ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<String> handleFeignNotFound(FeignException.NotFound ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.contentUTF8());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException ex2){
        return new ResponseEntity<>(ex2.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(OnlyPlacedOrdersUpdatedException.class)
    public ResponseEntity<String> handlePlacedOrdersException(OnlyPlacedOrdersUpdatedException ex3){
        return new ResponseEntity<>(ex3.getMessage(), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(OrderNotCancelledException.class)
    public ResponseEntity<String> handleOrderNotCancelledException(OrderNotCancelledException ex4){
        return new ResponseEntity<>(ex4.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> response = new HashMap<>();
        response.put("message", "Validation failed");
        response.put("error", ex.getBindingResult()
                .getFieldErrors().get(0).getField() + " must be greater than 0");

        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(InvalidOrderStatusTransitionException.class)
    public ResponseEntity<String> handleInvalidOrderTransitionException(InvalidOrderStatusTransitionException ex5){
        return new ResponseEntity<>(ex5.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
