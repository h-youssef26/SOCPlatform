package com.soc.backend_core.consumer;

import com.soc.backend_core.Entities.domain.UnifiedEvent;
import com.soc.backend_core.service.EventStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


/**
 * Consumes events from Kafka topics and forwards them
 * to the storage service for persistence.
 *
 * Handles:
 * - Network events
 * - Endpoint events
 * - Alert events
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EventConsumer {

    private final EventStorageService storageService;

    @KafkaListener(topics = "events.network", groupId = "soc-backend")
    public void consumeNetworkEvent(UnifiedEvent event) {
        handle(event, "network");
    }

    @KafkaListener(topics = "events.endpoint", groupId = "soc-backend")
    public void consumeEndpointEvent(UnifiedEvent event) {
        handle(event, "endpoint");
    }

    @KafkaListener(topics = "events.alerts", groupId = "soc-backend")
    public void consumeAlert(UnifiedEvent event) {
        handle(event, "alert");
    }

    private void handle(UnifiedEvent event, String source) {
        if (event == null) {
            log.error("Received null {} event", source);
            return;
        }

        log.info("[Kafka:{}] eventId={}", source, event.getEventId());

        storageService.storeEvent(event);
    }


    @KafkaListener(topics = "events.login", groupId = "soc-backend")
    public void consumeLoginEvent(UnifiedEvent event) {
        handle(event, "login");
    }


}
