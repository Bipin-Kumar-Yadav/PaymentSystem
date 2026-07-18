package com.bipin.wallet.service;

import java.util.UUID;

import com.bipin.wallet.dto.CreateWalletRequest;
import com.bipin.wallet.dto.TransferRequest;
import com.bipin.wallet.dto.WalletCreditRequest;
import com.bipin.wallet.dto.WalletDebitRequest;
import com.bipin.wallet.dto.WalletResponse;

public interface WalletService {
    WalletResponse createWallet(CreateWalletRequest request);
    void transfer(TransferRequest request);
    WalletResponse getBalance(UUID walletId);
    void credit(WalletCreditRequest request);
    void debit(WalletDebitRequest request);
}
