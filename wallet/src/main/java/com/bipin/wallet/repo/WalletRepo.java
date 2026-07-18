package com.bipin.wallet.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bipin.wallet.entity.Wallet;

public interface WalletRepo extends JpaRepository<Wallet,UUID>{
    Optional<Wallet> findByOwnerId(UUID ownerId);
}
