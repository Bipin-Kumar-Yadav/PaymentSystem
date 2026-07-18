package com.bipin.payment.kafka;

import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentEventProducer {
    public static final String PAYMENT_CREATED_TOPIC = "payment-created";
    public static final String WALLET_DEBITED_TOPIC = "wallet-debited";
    public static final String WALLET_CREDITED_TOPIC = "wallet-credited";
    public static final String PAYMENT_COMPLETED_TOPIC = "payment-completed";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public CompletableFuture<SendResult<String, String>> publish(String topic, String key, String message) {
        return kafkaTemplate.send(topic, key, message);
    }
}
