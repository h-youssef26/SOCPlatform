package com.soc.backend_core.service;

import com.soc.backend_core.Entities.domain.AlertMessage;
import com.soc.backend_core.Entities.domain.SoarCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.soc.backend_core.mapper.SoarCommandMapper;
import com.soc.backend_core.repository.jpa.SoarCommandRepository;
import lombok.RequiredArgsConstructor;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class SoarService {

    private static final Logger log = LoggerFactory.getLogger(SoarService.class);

    private final SoarCommandRepository commandRepository; // ← replaces the Map
    private final SoarCommandMapper     commandMapper;
    private final SimpMessagingTemplate messagingTemplate;

    // ── Kill Process ──────────────────────────────────────────────
    public SoarCommand killProcess(String deviceId,
                                   String targetProcess,
                                   String triggeredBy) {
        validate(deviceId, triggeredBy);
        if (targetProcess == null || targetProcess.isBlank())
            throw new IllegalArgumentException(
                    "targetProcess is required for KILL_PROCESS");

        SoarCommand command = buildCommand(
                "KILL_PROCESS", deviceId, targetProcess, null, triggeredBy);

        persist(command);
        pushAlertToDashboard(buildAlert(
                "COMMAND_EXECUTED", "HIGH", deviceId,
                "Process killed: " + targetProcess, "KILL_PROCESS"));

        return command;
    }

    // ── Block IP ──────────────────────────────────────────────────
    public SoarCommand blockIp(String deviceId,
                               String targetIp,
                               String triggeredBy) {
        validate(deviceId, triggeredBy);
        if (targetIp == null || targetIp.isBlank())
            throw new IllegalArgumentException(
                    "targetIp is required for BLOCK_IP");

        SoarCommand command = buildCommand(
                "BLOCK_IP", deviceId, null, targetIp, triggeredBy);

        persist(command);
        pushAlertToDashboard(buildAlert(
                "COMMAND_EXECUTED", "HIGH", deviceId,
                "IP blocked: " + targetIp, "BLOCK_IP"));

        return command;
    }

    // ── Isolate Host ──────────────────────────────────────────────
    public SoarCommand isolateHost(String deviceId, String triggeredBy) {
        validate(deviceId, triggeredBy);

        SoarCommand command = buildCommand(
                "ISOLATE_HOST", deviceId, null, null, triggeredBy);

        persist(command);
        pushAlertToDashboard(buildAlert(
                "COMMAND_EXECUTED", "CRITICAL", deviceId,
                "Host isolated: " + deviceId, "ISOLATE_HOST"));

        return command;
    }

    // ── Queries ───────────────────────────────────────────────────
    public List<SoarCommand> getAllCommands() {
        return commandRepository.findAll()
                .stream()
                .map(commandMapper::toDomain)
                .toList();
    }

    public Map<String, Object> getStats() {
        List<SoarCommand> commands = getAllCommands();
        return Map.of(
                "totalCommands",    commands.size(),
                "killProcessCount", commands.stream().filter(c -> "KILL_PROCESS" .equals(c.getCommandType())).count(),
                "blockIpCount",     commands.stream().filter(c -> "BLOCK_IP"     .equals(c.getCommandType())).count(),
                "isolateHostCount", commands.stream().filter(c -> "ISOLATE_HOST" .equals(c.getCommandType())).count()
        );
    }

    // ── WebSocket ─────────────────────────────────────────────────
    public void pushAlertToDashboard(AlertMessage alert) {
        log.info("Pushing alert to Dashboard: {}", alert.getMessage());
        messagingTemplate.convertAndSend("/topic/alerts", alert);
    }

    // ── Private helpers ───────────────────────────────────────────
    private void validate(String deviceId, String triggeredBy) {
        if (deviceId    == null || deviceId   .isBlank())
            throw new IllegalArgumentException("deviceId is required");
        if (triggeredBy == null || triggeredBy.isBlank())
            throw new IllegalArgumentException("triggeredBy is required");
    }

    private SoarCommand buildCommand(String type, String deviceId,
                                     String process, String ip,
                                     String triggeredBy) {
        return SoarCommand.builder()
                .commandId(UUID.randomUUID().toString())
                .commandType(type)
                .deviceId(deviceId)
                .targetProcess(process)
                .targetIp(ip)
                .triggeredBy(triggeredBy)
                .status("SENT")
                .createdAt(Instant.now())
                .build();
    }

    private void persist(SoarCommand command) {
        commandRepository.save(commandMapper.toRecord(command));
        log.info("SOAR [{}] persisted: commandId={}", command.getCommandType(),
                command.getCommandId());
    }

    private AlertMessage buildAlert(String type, String severity,
                                    String deviceId, String message,
                                    String commandType) {
        return AlertMessage.builder()
                .alertId(UUID.randomUUID().toString())
                .type(type).severity(severity)
                .deviceId(deviceId).message(message)
                .commandType(commandType).timestamp(Instant.now())
                .build();
    }
}
