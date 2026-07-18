package com.bipin.wallet.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bipin.wallet.dto.CreateWalletRequest;
import com.bipin.wallet.dto.TransferRequest;
import com.bipin.wallet.dto.WalletCreditRequest;
import com.bipin.wallet.dto.WalletDebitRequest;
import com.bipin.wallet.dto.WalletResponse;
import com.bipin.wallet.service.WalletService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<WalletResponse> createWallet(@Valid @RequestBody CreateWalletRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(walletService.createWallet(request));
    }
    
    @GetMapping("/{walletId}")
    public ResponseEntity<WalletResponse> getBalance(@PathVariable UUID walletId){
        return ResponseEntity.status(HttpStatus.OK).body(walletService.getBalance(walletId));
    }
    
    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@Valid @RequestBody TransferRequest req) {
        walletService.transfer(req);

        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/credit")
    public ResponseEntity<Void> credit(@Valid @RequestBody WalletCreditRequest req) {
       walletService.credit(req);
       return ResponseEntity.ok().build();
    }

    @PostMapping("/debit")
    public ResponseEntity<Void> debit(@Valid @RequestBody WalletDebitRequest request) {
       walletService.debit(request);
        
        return ResponseEntity.ok().build();
    }
    
    
    
    
}
