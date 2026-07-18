package com.bipin.wallet.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wallet-outbox")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OutboxEvent {
    @Id
    private UUID id;

    private String aggregateId;
    private String eventType;
    @Lob
    private String payload;

    @Enumerated(EnumType.STRING)
    private OutboxStatus status;
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate(){
        createdAt = Instant.now();
    }

}
