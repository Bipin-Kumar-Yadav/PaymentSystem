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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletCreditedEvent {
    private UUID walletId;
    private UUID ownerId;
    private BigDecimal amount;      // Amount credited
    private BigDecimal balance;     // Balance after credit
    private String currency;
    private Instant occurredAt;
}
