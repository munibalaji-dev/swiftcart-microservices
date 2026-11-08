package com.swiftcart.swiftcartorderservice.exceptions;

public class InsufficientStock extends RuntimeException {
    public InsufficientStock(String message) {
        super(message);
    }
}
