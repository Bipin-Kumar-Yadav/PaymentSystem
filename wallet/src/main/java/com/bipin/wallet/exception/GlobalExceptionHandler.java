package com.bipin.wallet.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bipin.wallet.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WalletAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleWalletAlreadyExists(WalletAlreadyExists ex, HttpServletRequest req){
        ErrorResponse errorResponse = ErrorResponse.builder()
                                        .timestamp(Instant.now())
                                        .status(HttpStatus.CONFLICT.value())
                                        .error("Wallet Already Exists")
                                        .message(ex.getMessage())
                                        .path(req.getRequestURI())
                                        .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(WalletNotFoundException.class )
    public ResponseEntity<ErrorResponse> handleWalletNotFoundException(WalletNotFoundException ex, HttpServletRequest req){
        ErrorResponse response = ErrorResponse.builder()
                                              .status(HttpStatus.NOT_FOUND.value())
                                              .timestamp(Instant.now())
                                              .error("Wallet Not Found")
                                              .message(ex.getMessage())
                                              .path(req.getRequestURI())
                                              .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InsufficientBalanceException.class )
    public ResponseEntity<ErrorResponse> handleInsufficientBalanceException(InsufficientBalanceException ex, HttpServletRequest req){
        ErrorResponse response = ErrorResponse.builder()
                                              .status(HttpStatus.BAD_REQUEST.value())
                                              .timestamp(Instant.now())
                                              .error("Insufficient Balance")
                                              .message(ex.getMessage())
                                              .path(req.getRequestURI())
                                              .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(TransferAlreadyProcessedException.class)
    public ResponseEntity<ErrorResponse> handleTransferAlreadyProcessed(TransferAlreadyProcessedException ex,
            HttpServletRequest req) {
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .timestamp(Instant.now())
                .error("Transfer Already Processed")
                .message(ex.getMessage())
                .path(req.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
