package com.bipin.wallet.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WalletEventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    public CompletableFuture<SendResult<String, String>> publish(String topic, String key, String message) {
        return kafkaTemplate.send(topic, key, message);
    }
}
