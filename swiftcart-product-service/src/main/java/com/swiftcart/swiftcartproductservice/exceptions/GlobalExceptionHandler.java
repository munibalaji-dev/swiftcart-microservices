package com.swiftcart.swiftcartproductservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(InsufficientStock.class)
    public ResponseEntity<String> handleInsufficientStock(InsufficientStock ex1){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex1.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> response = new HashMap<>();
        response.put("message", "Validation failed");
        response.put("error", ex.getBindingResult()
                .getFieldErrors().get(0).getDefaultMessage());

        return ResponseEntity.badRequest().body(response);
    }

}
