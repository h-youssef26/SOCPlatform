package com.soc.backend_core.controller;

import com.soc.backend_core.Entities.domain.AlertMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.UUID;


/**
 * REST controller that handles incoming security events
 * and routes them to Kafka for processing in the SOC pipeline.
 */


@Controller
@Slf4j
public class WebSocketController {

    /**
     * Handles dashboard ping requests and returns system status alert.
     *
     * @return system status alert message
     */

    // Dashboard sends a ping → server replies with current status
    @MessageMapping("/ping")
    @SendTo("/topic/alerts")
    public AlertMessage ping() {
        log.info("WebSocket ping received from Dashboard");

        return AlertMessage.builder()
                .alertId(UUID.randomUUID().toString())
                .type("SYSTEM_STATUS")
                .severity("LOW")
                .deviceId("system")
                .message("SOC Platform is running")
                .commandType("NONE")
                .timestamp(Instant.now())
                .build();
    }
}
