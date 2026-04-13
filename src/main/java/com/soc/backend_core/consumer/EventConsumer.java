package com.soc.backend_core.consumer;

import com.soc.backend_core.model.UnifiedEvent;
import com.soc.backend_core.service.EventStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventConsumer {

    private final EventStorageService storageService;

    @KafkaListener(
        topics = "events.network",
        groupId = "soc-backend"
    )
    public void consumeNetworkEvent(UnifiedEvent event) {
        if (event == null) {
            log.error("Received null network event!");
            return;
        }

        log.info("Received network event from Kafka: {}", event.getEventId());
        storageService.storeEvent(event);
    }

    @KafkaListener(
        topics = "events.endpoint",
        groupId = "soc-backend"
    )
    public void consumeEndpointEvent(UnifiedEvent event) {
        if (event == null) {
            log.error("Received null endpoint event!");
            return;
        }

        log.info("Received endpoint event from Kafka: {}", event.getEventId());
        storageService.storeEvent(event);
    }

    @KafkaListener(
        topics = "events.alerts",
        groupId = "soc-backend"
    )
    public void consumeAlert(UnifiedEvent event) {
        if (event == null) {
            log.error("Received null alert event!");
            return;
        }

        log.info("Received alert from Kafka: {}", event.getEventId());
        storageService.storeEvent(event);
    }
}