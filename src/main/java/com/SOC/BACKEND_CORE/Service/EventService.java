package com.SOC.BACKEND_CORE.Service;

import com.SOC.BACKEND_CORE.Model.*;
import com.SOC.BACKEND_CORE.Kafka.KafkaProducerService;
import org.springframework.stereotype.Service;
import com.SOC.BACKEND_CORE.DTO.NdrLogRequest;

import java.time.Instant;
import java.util.UUID;

@Service
public class EventService {

    private final KafkaProducerService kafkaProducerService;

    public EventService(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    public void processNetworkEvent(NdrLogRequest req) {

        SecurityEvent event = new SecurityEvent();

        event.setId(UUID.randomUUID().toString());

        Event eventType = new Event();
        eventType.setCategory("network");

        Network network = new Network();
        network.setSource_ip(req.getSrc_ip());
        network.setDestination_ip(req.getDst_ip());
        network.setProtocol(req.getProtocol());

        Device device = new Device();
        device.setId(req.getSensor());
        device.setType("sensor");

        event.setEvent(eventType);
        event.setNetwork(network);
        event.setDevice(device);
        event.setTimestamp(Instant.now());

        kafkaProducerService.sendEvent(event);
    }
}
