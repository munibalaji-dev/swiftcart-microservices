package com.swiftcart.swiftcartorderservice.exceptions;

public class OnlyPlacedOrdersUpdatedException extends RuntimeException {
    public OnlyPlacedOrdersUpdatedException(String message) {
        super(message);
    }
}
