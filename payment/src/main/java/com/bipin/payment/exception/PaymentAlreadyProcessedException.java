package com.bipin.payment.exception;

import java.util.UUID;

public class PaymentAlreadyProcessedException extends RuntimeException {

    public PaymentAlreadyProcessedException(String message) {
        super(message);
    }

    public PaymentAlreadyProcessedException(UUID paymentId) {
        super("Payment has already been processed. Payment ID: " + paymentId);
    }
}