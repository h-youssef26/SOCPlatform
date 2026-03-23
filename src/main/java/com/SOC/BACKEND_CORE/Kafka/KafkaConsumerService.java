package com.SOC.BACKEND_CORE.Kafka;

import com.SOC.BACKEND_CORE.Model.SecurityEvent;
import com.SOC.BACKEND_CORE.Repository.SecurityEventRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final SecurityEventRepository repository;

    public KafkaConsumerService(SecurityEventRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "security-events", groupId = "soc-group")
    public void consume(SecurityEvent event) {
        repository.save(event);
        System.out.println("Saved to PostgreSQL: " + event.getId());
    }
}
