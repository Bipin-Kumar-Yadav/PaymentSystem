package com.bipin.payment.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletCreditedEvent {
    private UUID paymentId;
    private UUID walletId;
    private UUID userId;
    private BigDecimal creditAmount;
    private BigDecimal currentBalance;
    private String currency;
    private Instant timestamp;
}
