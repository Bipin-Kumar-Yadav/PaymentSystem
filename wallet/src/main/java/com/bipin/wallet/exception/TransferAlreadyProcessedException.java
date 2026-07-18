package com.bipin.wallet.exception;

public class TransferAlreadyProcessedException extends RuntimeException {
    public TransferAlreadyProcessedException(String message) {
        super(message);
    }
}
