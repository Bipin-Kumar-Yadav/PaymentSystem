package com.bipin.wallet.worker;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bipin.wallet.dto.KafkaEvent;
import com.bipin.wallet.entity.OutboxEvent;
import com.bipin.wallet.entity.OutboxStatus;
import com.bipin.wallet.kafka.WalletEventProducer;
import com.bipin.wallet.repo.OutboxRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxWorker {
    private final OutboxRepo outboxRepo;
    private final WalletEventProducer producer;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void publishEvents(){
        List<OutboxEvent> events = outboxRepo.findTop100ByStatusOrderByCreatedAtAsc(OutboxStatus.NEW);

        for (OutboxEvent event : events) {
            try {
                KafkaEvent kafkaEvent = KafkaEvent.builder()
                        .eventType(event.getEventType())
                        .payload(objectMapper.readTree(event.getPayload()))
                        .build();

                producer.publish(
                        "wallet-event",
                        event.getAggregateId(),
                        objectMapper.writeValueAsString(kafkaEvent)
                ).get();
                event.setStatus(OutboxStatus.SENT);
                log.info("Published event : {}", event.getId());
            } catch (Exception ex) {
                log.error("Failed to publish {}", event.getId(), ex);
            }
        }
    }
}
