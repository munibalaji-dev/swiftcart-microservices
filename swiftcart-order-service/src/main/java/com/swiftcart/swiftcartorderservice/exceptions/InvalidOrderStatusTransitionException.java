package com.swiftcart.swiftcartorderservice.exceptions;

public class InvalidOrderStatusTransitionException extends RuntimeException {
    public InvalidOrderStatusTransitionException(String message) {
        super(message);
    }
}


/* This exception will be used when someone tries an invalid transition, for example:
- PLACED → DELIVERED
- DELIVERED → SHIPPED
- CANCELLED → CONFIRMED */
