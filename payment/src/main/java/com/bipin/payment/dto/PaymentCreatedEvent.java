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
public class PaymentCreatedEvent {
    private UUID paymentId;
    private UUID senderWalletId;
    private UUID receiverWalletId;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private Instant createdAt;
}
