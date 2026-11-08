package com.swiftcart.swiftcartproductservice.exceptions;

public class InsufficientStock extends RuntimeException {
    public InsufficientStock(String message) {
        super(message);
    }
}
