package com.SOC.BACKEND_CORE.Kafka;

import com.SOC.BACKEND_CORE.Model.SecurityEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {


    private final KafkaTemplate<String, SecurityEvent> kafkaTemplate;

    private static final String TOPIC = "security-events";

    public KafkaProducerService(KafkaTemplate<String, SecurityEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(SecurityEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}
