package com.swiftcart.swiftcartorderservice.exceptions;

public class OrderNotCancelledException extends RuntimeException {
    public OrderNotCancelledException(String message) {
        super(message);
    }
}
