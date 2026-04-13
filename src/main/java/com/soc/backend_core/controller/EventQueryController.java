package com.soc.backend_core.controller;

import com.soc.backend_core.Entities.elastic.EventDocument;
import com.soc.backend_core.service.EventQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/query")
@RequiredArgsConstructor
public class EventQueryController {

    private final EventQueryService queryService;

    // Get all events
    @GetMapping("/events")
    public ResponseEntity<List<EventDocument>> getAllEvents() {
        return ResponseEntity.ok(queryService.getAllEvents());
    }

    // Get events by device
    @GetMapping("/events/device/{deviceId}")
    public ResponseEntity<List<EventDocument>> getByDevice(
            @PathVariable String deviceId) {
        return ResponseEntity.ok(queryService.getEventsByDevice(deviceId));
    }

    // Get events by severity
    @GetMapping("/events/severity/{severity}")
    public ResponseEntity<List<EventDocument>> getBySeverity(
            @PathVariable String severity) {
        return ResponseEntity.ok(queryService.getEventsBySeverity(severity));
    }

    // Get events by source
    @GetMapping("/events/source/{source}")
    public ResponseEntity<List<EventDocument>> getBySource(
            @PathVariable String source) {
        return ResponseEntity.ok(queryService.getEventsBySource(source));
    }

    // Get events by type
    @GetMapping("/events/type/{eventType}")
    public ResponseEntity<List<EventDocument>> getByType(
            @PathVariable String eventType) {
        return ResponseEntity.ok(queryService.getEventsByType(eventType));
    }

    // Get stats summary
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        long total = queryService.getTotalEventCount();
        long high = queryService.getEventsBySeverity("HIGH").size();
        long medium = queryService.getEventsBySeverity("MEDIUM").size();
        long low = queryService.getEventsBySeverity("LOW").size();
        long ndr = queryService.getEventsBySource("NDR").size();
        long edr = queryService.getEventsBySource("EDR").size();

        return ResponseEntity.ok(Map.of(
                "totalEvents", total,
                "highSeverity", high,
                "mediumSeverity", medium,
                "lowSeverity", low,
                "ndrEvents", ndr,
                "edrEvents", edr
        ));
    }
}
