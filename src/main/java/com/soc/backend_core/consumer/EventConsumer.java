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


    /**
     * Consumes network events from Kafka topic "events.network"
     * and stores them in the system.
     *
     * @param event the incoming network event
     */

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

    /**
     * Consumes endpoint events from Kafka topic "events.endpoint"
     * and stores them into the system via storage service.
     *
     * @param event incoming endpoint security event
     */

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

    /**
     * Consumes alert events from Kafka topic "events.alerts"
     * and stores them for further processing and logging.
     *
     * @param event incoming alert event
     */


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
