package com.bipin.payment.exception;

import java.util.UUID;

public class PaymentNotFoundException extends RuntimeException{
    public PaymentNotFoundException(String message) {
        super(message);
    }

    public PaymentNotFoundException(UUID paymentId) {
        super("Payment not found with ID: " + paymentId);
    }

    public PaymentNotFoundException(String field, Object value) {
        super("Payment not found with " + field + ": " + value);
    }
}
