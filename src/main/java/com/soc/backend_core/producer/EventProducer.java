package com.soc.backend_core.producer;

import com.soc.backend_core.Entities.domain.UnifiedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Produces security events to Kafka topics for async processing.
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class EventProducer {
    
    private final KafkaTemplate<String, UnifiedEvent> kafkaTemplate;

    public void sendNetworkEvent(UnifiedEvent event) {
        kafkaTemplate.send("events.network", event.getEventId(), event);
        log.info("Network event sent to Kafka: {}", event.getEventId());
    }

    public void sendEndpointEvent(UnifiedEvent event) {
        kafkaTemplate.send("events.endpoint", event.getEventId(), event);
        log.info("Endpoint event sent to Kafka: {}", event.getEventId());
    }

    public void sendAlert(UnifiedEvent event) {
        kafkaTemplate.send("events.alerts", event.getEventId(), event);
        log.info("Alert sent to Kafka: {}", event.getEventId());
    }

    public void sendLoginEvent(UnifiedEvent event) {
        kafkaTemplate.send("events.login", event.getEventId(), event);
        log.info("Login event sent to Kafka: {}", event.getEventId());
    }

}
