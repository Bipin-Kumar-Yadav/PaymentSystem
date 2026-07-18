package com.bipin.wallet.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bipin.wallet.dto.CreateWalletRequest;
import com.bipin.wallet.dto.TransferRequest;
import com.bipin.wallet.dto.WalletCreditRequest;
import com.bipin.wallet.dto.WalletCreditedEvent;
import com.bipin.wallet.dto.WalletDebitRequest;
import com.bipin.wallet.dto.WalletDebitedEvent;
import com.bipin.wallet.dto.WalletResponse;
import com.bipin.wallet.entity.OutboxEvent;
import com.bipin.wallet.entity.OutboxStatus;
import com.bipin.wallet.entity.Wallet;
import com.bipin.wallet.exception.InsufficientBalanceException;
import com.bipin.wallet.exception.TransferAlreadyProcessedException;
import com.bipin.wallet.exception.WalletAlreadyExists;
import com.bipin.wallet.exception.WalletNotFoundException;
import com.bipin.wallet.redis.IdempotencyService;
import com.bipin.wallet.repo.OutboxRepo;
import com.bipin.wallet.repo.WalletRepo;
import com.fasterxml.jackson.databind.ObjectMapper;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletServiceImp  implements WalletService{

    private final WalletRepo walletRepo;
    private final OutboxRepo outboxRepo;
    private final ObjectMapper objectMapper;
    private final IdempotencyService idempotencyService;

    @Override
    @Transactional
    public WalletResponse createWallet(CreateWalletRequest request) {
        if(walletRepo.findByOwnerId(request.getOwnerId()).isPresent()){
            throw new WalletAlreadyExists("Wallet already exists");
        }
        
        Wallet wallet = Wallet.builder()
                              .id(UUID.randomUUID())
                              .ownerId(request.getOwnerId())
                              .balance(BigDecimal.ZERO)
                              .currency(request.getCurrency())
                              .build();
        
        Wallet savedWallet = walletRepo.save(wallet);
        WalletResponse response = toWalletResponse(savedWallet);

        saveOutboxEvent(savedWallet.getId().toString(),"WalletCreated",response);
        return response;

    }
    
    @Override
    @Transactional
    public void transfer(TransferRequest request){
        String key = "wallet:idempotency:" + request.getIdempotencyKey();

        if(!idempotencyService.acquire(key)){
            throw new TransferAlreadyProcessedException(
                    "A transfer with idempotency key '" + request.getIdempotencyKey() + "' has already been submitted");
        }

        try {
            WalletCreditRequest req = WalletCreditRequest.builder()
                                        .amount(request.getAmount())
                                        .walletId(request.getToWalletId())
                                        .build();

            WalletDebitRequest req1 = WalletDebitRequest.builder()
                                      .amount(request.getAmount())
                                      .walletId(request.getFromWalletId())
                                      .build();

            debit(req1);
            credit(req);
            idempotencyService.markSuccess(key);
        } catch (Exception e) {
            idempotencyService.release(key);
            throw e;
        }
    }

    @Transactional
    public void debit(WalletDebitRequest req){
        Wallet wallet = applyDelta(req.getWalletId(), req.getAmount().negate());
        WalletDebitedEvent event = WalletDebitedEvent.builder()
                .walletId(wallet.getId())
                .ownerId(wallet.getOwnerId())
                .amount(req.getAmount())
                .balance(wallet.getBalance())
                .currency(wallet.getCurrency())
                .build();

        saveOutboxEvent(
                wallet.getId().toString(),
                "WalletDebited",
                event
        );
    }

    @Transactional
    public void credit(WalletCreditRequest request){
        Wallet wallet = applyDelta(request.getWalletId(), request.getAmount());

        WalletCreditedEvent event = WalletCreditedEvent.builder()
                .walletId(wallet.getId())
                .ownerId(wallet.getOwnerId())
                .amount(request.getAmount())
                .balance(wallet.getBalance())
                .currency(wallet.getCurrency())
                .build();

        saveOutboxEvent(
                wallet.getId().toString(),
                "WalletCredited",
                event
        );
    }


    @Retryable(
            retryFor = ObjectOptimisticLockingFailureException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 50, multiplier = 2)
    )
    @Transactional
    public Wallet applyDelta(UUID walletId, BigDecimal delta){

        Wallet wallet = walletRepo.findById(walletId)
                                    .orElseThrow(()-> new WalletNotFoundException("Wallet Not Found: "+walletId));
        
        BigDecimal newBalance = wallet.getBalance().add(delta);

        if(newBalance.compareTo(BigDecimal.ZERO)<0){
            throw new InsufficientBalanceException(
                    "Insufficient balance");
        }

        wallet.setBalance(newBalance);
        return walletRepo.save(wallet);
        
    }
    @Transactional(readOnly = true)
    public WalletResponse getBalance(UUID walletId) {
        Wallet wallet = walletRepo.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found: " + walletId));
        return toWalletResponse(wallet);
    }

    private void saveOutboxEvent(
            String aggregateId,
            String eventType,
            Object payload) {

        OutboxEvent outboxEvent = OutboxEvent.builder()
                .id(UUID.randomUUID())
                .aggregateId(aggregateId)
                .eventType(eventType)
                .payload(convertToJson(payload))
                .status(OutboxStatus.NEW)
                .build();

        outboxRepo.save(outboxEvent);
    }

    private WalletResponse toWalletResponse(Wallet wallet){
        WalletResponse walletResponse =WalletResponse.builder()
                                            .ownerId(wallet.getOwnerId())
                                            .walletId(wallet.getId())
                                            .balance(wallet.getBalance())
                                            .currency(wallet.getCurrency())
                                            .build();
        return walletResponse;
    }

    private String convertToJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }

}
