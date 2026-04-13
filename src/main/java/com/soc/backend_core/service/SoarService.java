package com.soc.backend_core.service;

import com.soc.backend_core.Entities.domain.AlertMessage;
import com.soc.backend_core.Entities.domain.SoarCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/**
 * SOAR service responsible for executing security automation actions
 * such as killing processes, blocking IPs, and isolating hosts.
 * Also sends real-time alerts to dashboard via WebSocket.
 */

@Service
public class SoarService {

    private static final Logger log =
            LoggerFactory.getLogger(SoarService.class);

    // Stores all commands in memory
    private final Map<String, SoarCommand> commandStore =
            new ConcurrentHashMap<>();

    // Used to push alerts to Dashboard via WebSocket
    private final SimpMessagingTemplate messagingTemplate;

    public SoarService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // ─── Kill Process ────────────────────────────────────────────
    /**
     * Executes process termination command on target device
     * and sends alert to dashboard.
     */

    public SoarCommand killProcess(String deviceId,
                                   String targetProcess,
                                   String triggeredBy) {
        log.info("SOAR: Kill process [{}] on device [{}]",
                targetProcess, deviceId);

        SoarCommand command = SoarCommand.builder()
                .commandId(UUID.randomUUID().toString())
                .commandType("KILL_PROCESS")
                .deviceId(deviceId)
                .targetProcess(targetProcess)
                .triggeredBy(triggeredBy)
                .status("SENT")
                .createdAt(Instant.now())
                .build();

        commandStore.put(command.getCommandId(), command);

        pushAlertToDashboard(AlertMessage.builder()
                .alertId(UUID.randomUUID().toString())
                .type("COMMAND_EXECUTED")
                .severity("HIGH")
                .deviceId(deviceId)
                .message("Process killed: " + targetProcess)
                .commandType("KILL_PROCESS")
                .timestamp(Instant.now())
                .build());

        return command;
    }

    // ─── Block IP ────────────────────────────────────────────────
    /**
     * Blocks an IP address on target device
     * and sends alert to dashboard.
     */

    public SoarCommand blockIp(String deviceId,
                               String targetIp,
                               String triggeredBy) {
        log.info("SOAR: Block IP [{}] on device [{}]",
                targetIp, deviceId);

        SoarCommand command = SoarCommand.builder()
                .commandId(UUID.randomUUID().toString())
                .commandType("BLOCK_IP")
                .deviceId(deviceId)
                .targetIp(targetIp)
                .triggeredBy(triggeredBy)
                .status("SENT")
                .createdAt(Instant.now())
                .build();

        commandStore.put(command.getCommandId(), command);

        pushAlertToDashboard(AlertMessage.builder()
                .alertId(UUID.randomUUID().toString())
                .type("COMMAND_EXECUTED")
                .severity("HIGH")
                .deviceId(deviceId)
                .message("IP blocked: " + targetIp)
                .commandType("BLOCK_IP")
                .timestamp(Instant.now())
                .build());

        return command;
    }

    // ─── Isolate Host ─────────────────────────────────────────────
    /**
     * Isolates a host from network and triggers alert notification.
     */

    public SoarCommand isolateHost(String deviceId,
                                   String triggeredBy) {
        log.info("SOAR: Isolate host [{}]", deviceId);

        SoarCommand command = SoarCommand.builder()
                .commandId(UUID.randomUUID().toString())
                .commandType("ISOLATE_HOST")
                .deviceId(deviceId)
                .triggeredBy(triggeredBy)
                .status("SENT")
                .createdAt(Instant.now())
                .build();

        commandStore.put(command.getCommandId(), command);

        pushAlertToDashboard(AlertMessage.builder()
                .alertId(UUID.randomUUID().toString())
                .type("COMMAND_EXECUTED")
                .severity("CRITICAL")
                .deviceId(deviceId)
                .message("Host isolated: " + deviceId)
                .commandType("ISOLATE_HOST")
                .timestamp(Instant.now())
                .build());

        return command;
    }

    // ─── Get All Commands ─────────────────────────────────────────
    /**
     * Returns all executed SOAR commands stored in memory.
     */

    public List<SoarCommand> getAllCommands() {
        return new ArrayList<>(commandStore.values());
    }

    // ─── Push Alert to Dashboard ──────────────────────────────────
    /**
     * Sends real-time alert message to WebSocket dashboard.
     *
     * @param alert alert message
     */

    public void pushAlertToDashboard(AlertMessage alert) {
        log.info("Pushing alert to Dashboard: {}", alert.getMessage());
        messagingTemplate.convertAndSend("/topic/alerts", alert);
    }
}
