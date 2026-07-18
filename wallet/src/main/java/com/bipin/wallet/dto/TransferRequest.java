package com.bipin.wallet.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferRequest {
    @NotBlank
    private String idempotencyKey;
    @NotNull
    private UUID fromWalletId;
    @NotNull
    private UUID toWalletId;
    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;
    @NotBlank
    private String currency;
}
