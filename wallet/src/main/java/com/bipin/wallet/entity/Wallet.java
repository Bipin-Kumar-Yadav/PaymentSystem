package com.bipin.wallet.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wallets")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Wallet {
    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID ownerId;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String currency;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist
    void onCreate(){
        createdAt = Instant.now();
        updatedAt = createdAt;
        if(balance == null) balance = BigDecimal.ZERO;
    }

    @PreUpdate
    void onUpdate(){
        updatedAt = Instant.now();
    }
}
