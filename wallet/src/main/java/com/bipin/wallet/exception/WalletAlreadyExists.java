package com.bipin.wallet.exception;

public class WalletAlreadyExists extends RuntimeException{
    public WalletAlreadyExists(String message){
        super(message);
    }
}
