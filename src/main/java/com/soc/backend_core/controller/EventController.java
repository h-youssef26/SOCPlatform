package com.soc.backend_core.controller;

import com.soc.backend_core.dto.EndpointEventRequest;
import com.soc.backend_core.dto.LoginEventRequest;
import com.soc.backend_core.dto.NetworkEventRequest;
import com.soc.backend_core.Entities.domain.UnifiedEvent;
import com.soc.backend_core.producer.EventProducer;
import com.soc.backend_core.translator.EventTranslator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;



/**
 * REST controller that receives security events
 * and publishes them to Kafka for processing.
 */

@Validated
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventProducer producer;
    private final EventTranslator translator;

    @PostMapping("/network")
    public ResponseEntity<String> receiveNetworkEvent(
            @RequestBody @Valid NetworkEventRequest request) {

        UnifiedEvent event = translator.fromNetwork(request);
        producer.sendNetworkEvent(event);

        return ResponseEntity.ok("Network event accepted");
    }

    @PostMapping("/endpoint")
    public ResponseEntity<String> receiveEndpointEvent(
            @RequestBody @Valid EndpointEventRequest request) {

        UnifiedEvent event = translator.fromEndpoint(request);
        producer.sendEndpointEvent(event);

        return ResponseEntity.ok("Endpoint event accepted");
    }

    @PostMapping("/login")
    public ResponseEntity<String> receiveLoginEvent(
            @RequestBody @Valid LoginEventRequest request) {

        UnifiedEvent event = translator.fromLogin(request);
        producer.sendLoginEvent(event);

        return ResponseEntity.ok("Login event accepted");
    }
}
