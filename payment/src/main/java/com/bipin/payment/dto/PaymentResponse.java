package com.bipin.payment.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.bipin.payment.entity.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private UUID id;
    private UUID senderWalletId;
    private UUID receiverWalletId;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private PaymentStatus status;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
}
