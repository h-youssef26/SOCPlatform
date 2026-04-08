package com.soc.backend_core.consumer;

import com.soc.backend_core.model.UnifiedEvent;
import com.soc.backend_core.service.EventStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventConsumer {
    private static final Logger log = LoggerFactory.getLogger(EventConsumer.class);
    private final EventStorageService storageService;

    @KafkaListener(
        topics = "events.network",
        groupId = "soc-backend"
    )
    public void consumeNetworkEvent(UnifiedEvent event) {
        log.info("Received network event from Kafka: {}", event.getEventId());
        storageService.storeEvent(event);
    }

    @KafkaListener(
        topics = "events.endpoint",
        groupId = "soc-backend"
    )
    public void consumeEndpointEvent(UnifiedEvent event) {
        log.info("Received endpoint event from Kafka: {}", event.getEventId());
        storageService.storeEvent(event);
    }

    @KafkaListener(
        topics = "events.alerts",
        groupId = "soc-backend"
    )
    public void consumeAlert(UnifiedEvent event) {
        log.info("Received alert from Kafka: {}", event.getEventId());
        storageService.storeEvent(event);
    }
}