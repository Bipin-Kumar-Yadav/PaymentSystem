package com.bipin.wallet.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;

import jakarta.validation.constraints.NotNull;
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
public class WalletCreditRequest {
    @NotNull(message = "Wallet id is required!")
    private UUID walletId;
    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;
}
