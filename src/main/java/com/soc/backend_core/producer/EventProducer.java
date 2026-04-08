package com.soc.backend_core.producer;

import com.soc.backend_core.model.UnifiedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventProducer {
    private static final Logger log = LoggerFactory.getLogger(EventProducer.class);
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
}