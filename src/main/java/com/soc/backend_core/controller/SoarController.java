package com.soc.backend_core.controller;

import com.soc.backend_core.dto.SoarCommandRequest;
import com.soc.backend_core.Entities.domain.AlertMessage;
import com.soc.backend_core.Entities.domain.SoarCommand;
import com.soc.backend_core.service.SoarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * REST controller that handles incoming security events
 * and routes them to Kafka for processing in the SOC pipeline.
 */

@Validated
@RestController
@RequestMapping("/api/soar")
@Slf4j
public class SoarController {

    private final SoarService soarService;

    public SoarController(SoarService soarService) {
        this.soarService = soarService;
    }

    /**
     * Sends a command to terminate a process on a target device.
     *
     * @param request SOAR command request payload
     * @return created SOAR command
     */

    // ─── Kill Process ─────────────────────────────────────────────

    @PostMapping("/kill-process")
    public ResponseEntity<SoarCommand> killProcess(
            @Valid @RequestBody SoarCommandRequest request) {

        log.info("Received kill-process command for device: {}",
                request.getDeviceId());

        SoarCommand command = soarService.killProcess(
                request.getDeviceId(),
                request.getTargetProcess(),
                request.getTriggeredBy()
        );

        return ResponseEntity.ok(command);
    }

    // ─── Block IP ─────────────────────────────────────────────────

    /**
     * Sends a command to block an IP address on a target device.
     *
     * @param request SOAR command request payload
     * @return created SOAR command
     */

    @PostMapping("/block-ip")
    public ResponseEntity<SoarCommand> blockIp(
            @Valid @RequestBody SoarCommandRequest request) {

        log.info("Received block-ip command for device: {}",
                request.getDeviceId());

        SoarCommand command = soarService.blockIp(
                request.getDeviceId(),
                request.getTargetIp(),
                request.getTriggeredBy()
        );

        return ResponseEntity.ok(command);
    }

    // ─── Isolate Host ─────────────────────────────────────────────
    /**
     * Isolates a device from the network for security containment.
     *
     * @param request SOAR command request payload
     * @return created SOAR command
     */

    @PostMapping("/isolate")
    public ResponseEntity<SoarCommand> isolateHost(
            @Valid @RequestBody SoarCommandRequest request) {

        log.info("Received isolate command for device: {}",
                request.getDeviceId());

        SoarCommand command = soarService.isolateHost(
                request.getDeviceId(),
                request.getTriggeredBy()
        );

        return ResponseEntity.ok(command);
    }

    // ─── Get All Commands ─────────────────────────────────────────

    /**
     * Retrieves all executed SOAR commands stored in memory.
     */

    @GetMapping("/commands")
    public ResponseEntity<List<SoarCommand>> getAllCommands() {
        return ResponseEntity.ok(soarService.getAllCommands());
    }

    // ─── Manual Alert Push (for testing) ─────────────────────────
    /**
     * Sends a test alert to the WebSocket dashboard.
     *
     * Used for verifying real-time alert system.
     */

    @PostMapping("/test-alert")
    public ResponseEntity<String> testAlert() {

        soarService.pushAlertToDashboard(
                AlertMessage.builder()
                        .alertId(UUID.randomUUID().toString())
                        .type("ATTACK_DETECTED")
                        .severity("HIGH")
                        .deviceId("test-device")
                        .message("Test alert from SOAR")
                        .commandType("NONE")
                        .timestamp(Instant.now())
                        .build()
        );

        return ResponseEntity.ok("Test alert pushed to Dashboard");
    }

    // ─── Stats ────────────────────────────────────────────────────
    /**
     * Returns statistics about executed SOAR commands.
     * Includes counts for kill, block, and isolate actions.
     */

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        return ResponseEntity.ok(soarService.getStats());
    }
}
