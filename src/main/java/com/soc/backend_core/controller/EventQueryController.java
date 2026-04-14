package com.soc.backend_core.controller;

import com.soc.backend_core.Entities.elastic.EventDocument;
import com.soc.backend_core.service.EventQueryService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;




import java.util.List;

/**
 * Provides APIs for querying stored security events
 * from Elasticsearch.
 */

@Slf4j
@RestController
@Validated
@RequestMapping("/api/query")
@RequiredArgsConstructor
public class EventQueryController {

    private final EventQueryService queryService;


    /**
     * Returns all stored security events.
     */


    // Get all events
    @GetMapping("/events")
    public ResponseEntity<List<EventDocument>> getAllEvents() {
        return ResponseEntity.ok(queryService.getAllEvents());
    }




    /**
     * Retrieves events filtered by device ID.
     *
     * @param deviceId target device identifier
     */

    // Get events by device
    @GetMapping("/events/device/{deviceId}")
    public ResponseEntity<List<EventDocument>> getByDevice(
            @PathVariable @NotBlank String deviceId) {
        return ResponseEntity.ok(queryService.getEventsByDevice(deviceId));
    }

    /**
     * Retrieves events filtered by severity level.
     *
     * @param severity event severity (LOW, MEDIUM, HIGH)
     */

    // Get events by severity
    @GetMapping("/events/severity/{severity}")
    public ResponseEntity<List<EventDocument>> getBySeverity(
            @PathVariable @NotBlank String severity) {
        return ResponseEntity.ok(queryService.getEventsBySeverity(severity));
    }

    /**
     * Retrieves events filtered by source system (NDR/EDR).
     *
     * @param source event source
     */

    // Get events by source
    @GetMapping("/events/source/{source}")
    public ResponseEntity<List<EventDocument>> getBySource(
            @PathVariable @NotBlank String source) {
        return ResponseEntity.ok(queryService.getEventsBySource(source));
    }

    /**
     * Retrieves events filtered by event type.
     *
     * @param eventType type of security event
     */

    // Get events by type
    @GetMapping("/events/type/{eventType}")
    public ResponseEntity<List<EventDocument>> getByType(
            @PathVariable @NotBlank String eventType) {
        return ResponseEntity.ok(queryService.getEventsByType(eventType));
    }

    /**
     * Returns aggregated statistics of stored security events
     * including severity and source distribution.
     */

    // Get stats summary
    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        return ResponseEntity.ok(queryService.getStats());
    }
}
