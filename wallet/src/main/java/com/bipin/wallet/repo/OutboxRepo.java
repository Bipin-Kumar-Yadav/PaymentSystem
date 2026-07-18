package com.bipin.wallet.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bipin.wallet.entity.OutboxEvent;
import com.bipin.wallet.entity.OutboxStatus;

public interface OutboxRepo extends JpaRepository<OutboxEvent,UUID>{
    List<OutboxEvent> findTop100ByStatusOrderByCreatedAtAsc(OutboxStatus status);
}
