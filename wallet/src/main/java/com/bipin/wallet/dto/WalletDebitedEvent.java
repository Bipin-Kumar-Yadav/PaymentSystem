package com.bipin.wallet.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletDebitedEvent {
    private UUID walletId;
    private UUID ownerId;
    private BigDecimal amount;      // Amount debited
    private BigDecimal balance;     // Balance after debit
    private String currency;
    private Instant occurredAt;
}
