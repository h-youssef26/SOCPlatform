package com.soc.backend_core.controller;

import com.soc.backend_core.dto.SoarCommandRequest;
import com.soc.backend_core.Entities.domain.AlertMessage;
import com.soc.backend_core.Entities.domain.SoarCommand;
import com.soc.backend_core.service.SoarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/soar")
@Slf4j
public class SoarController {

    private final SoarService soarService;

    public SoarController(SoarService soarService) {
        this.soarService = soarService;
    }

    // ─── Kill Process ─────────────────────────────────────────────

    @PostMapping("/kill-process")
    public ResponseEntity<SoarCommand> killProcess(
            @RequestBody SoarCommandRequest request) {

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

    @PostMapping("/block-ip")
    public ResponseEntity<SoarCommand> blockIp(
            @RequestBody SoarCommandRequest request) {

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

    @PostMapping("/isolate")
    public ResponseEntity<SoarCommand> isolateHost(
            @RequestBody SoarCommandRequest request) {

        log.info("Received isolate command for device: {}",
                request.getDeviceId());

        SoarCommand command = soarService.isolateHost(
                request.getDeviceId(),
                request.getTriggeredBy()
        );

        return ResponseEntity.ok(command);
    }

    // ─── Get All Commands ─────────────────────────────────────────

    @GetMapping("/commands")
    public ResponseEntity<List<SoarCommand>> getAllCommands() {
        return ResponseEntity.ok(soarService.getAllCommands());
    }

    // ─── Manual Alert Push (for testing) ─────────────────────────

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

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {

        List<SoarCommand> commands = soarService.getAllCommands();

        long killCount = commands.stream()
                .filter(c -> c.getCommandType().equals("KILL_PROCESS"))
                .count();

        long blockCount = commands.stream()
                .filter(c -> c.getCommandType().equals("BLOCK_IP"))
                .count();

        long isolateCount = commands.stream()
                .filter(c -> c.getCommandType().equals("ISOLATE_HOST"))
                .count();

        return ResponseEntity.ok(Map.of(
                "totalCommands", commands.size(),
                "killProcessCount", killCount,
                "blockIpCount", blockCount,
                "isolateHostCount", isolateCount
        ));
    }
}
