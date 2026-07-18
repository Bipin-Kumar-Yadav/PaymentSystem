package com.bipin.wallet.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
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
public class CreateWalletRequest {
    @NotNull(message = "Owner id is required!")
    private UUID ownerId;
    @NotBlank(message = "Currency is required!")
    private String currency;
}
