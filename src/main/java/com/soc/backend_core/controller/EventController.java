package com.soc.backend_core.controller;

import com.soc.backend_core.dto.EndpointEventRequest;
import com.soc.backend_core.dto.LoginEventRequest;
import com.soc.backend_core.dto.NetworkEventRequest;
import com.soc.backend_core.model.UnifiedEvent;
import com.soc.backend_core.producer.EventProducer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventProducer producer;

    @PostMapping("/network")
    public ResponseEntity<String> receiveNetworkEvent(
            @RequestBody @Valid NetworkEventRequest request) {

        UnifiedEvent event = UnifiedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .deviceId(request.getDeviceId())
                .eventType(request.getEventType())
                .sourceIp(request.getSrcIp())
                .destinationIp(request.getDestIp())
                .severity("MEDIUM")
                .source("NDR")
                .timestamp(Instant.now())
                .raw(request.getRaw())
                .build();

        producer.sendNetworkEvent(event);
        return ResponseEntity.ok("Network event accepted");
    }

    @PostMapping("/endpoint")
    public ResponseEntity<String> receiveEndpointEvent(
            @RequestBody @Valid EndpointEventRequest request) {

        UnifiedEvent event = UnifiedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .deviceId(request.getDeviceId())
                .eventType(request.getEventType())
                .process(request.getProcess())
                .user(request.getUser())
                .severity("LOW")
                .source("EDR")
                .timestamp(Instant.now())
                .raw(request.getRaw())
                .build();

        producer.sendEndpointEvent(event);
        return ResponseEntity.ok("Endpoint event accepted");
    }

    @PostMapping("/login")
    public ResponseEntity<String> receiveLoginEvent(
            @RequestBody @Valid LoginEventRequest request) {

        UnifiedEvent event = UnifiedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .deviceId(request.getDeviceId())
                .eventType("login_attempt")
                .user(request.getUser())
                .sourceIp(request.getSourceIp())
                .severity(request.isFailed() ? "HIGH" : "LOW")
                .source("EDR")
                .timestamp(Instant.now())
                .build();

        producer.sendEndpointEvent(event);
        return ResponseEntity.ok("Login event accepted");
    }
}