package com.soc.backend_core.controller;

import com.soc.backend_core.model.AlertMessage;
import com.soc.backend_core.service.SoarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.UUID;

@Controller
@Slf4j
public class WebSocketController {

    

    private final SoarService soarService;

    public WebSocketController(SoarService soarService) {
        this.soarService = soarService;
    }

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