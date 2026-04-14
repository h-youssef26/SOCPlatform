package com.soc.backend_core.service;

import com.soc.backend_core.Entities.elastic.EventDocument;
import com.soc.backend_core.Entities.jpa.EventRecord;
import com.soc.backend_core.repository.elastic.EventDocumentRepository;
import com.soc.backend_core.repository.jpa.EventRecordRepository;
import org.springframework.stereotype.Service;
import java.util.Set;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Provides query APIs for retrieving stored security events
 * from Elasticsearch and PostgreSQL.
 */

@Service
public class EventQueryService {

    private final EventDocumentRepository elasticsearchRepo;
    private final EventRecordRepository postgresRepo;

    private static final Set<String> VALID_SEVERITIES =
            Set.of("LOW", "MEDIUM", "HIGH", "CRITICAL");

    private static final Set<String> VALID_SOURCES =
            Set.of("NDR", "EDR");

    public EventQueryService(EventDocumentRepository elasticsearchRepo,
                             EventRecordRepository postgresRepo) {
        this.elasticsearchRepo = elasticsearchRepo;
        this.postgresRepo = postgresRepo;
    }

    public List<EventDocument> getAllEvents() {
        List<EventDocument> results = new ArrayList<>();
        elasticsearchRepo.findAll().forEach(results::add);
        return results;
    }

    public List<EventDocument> getEventsByDevice(String deviceId) {

        if (deviceId == null || deviceId.isBlank()) {
            throw new IllegalArgumentException("deviceId cannot be empty");
        }

        return elasticsearchRepo.findByDeviceId(deviceId);
    }


    public List<EventDocument> getEventsBySeverity(String severity) {
        if (severity == null || severity.isBlank())
            throw new IllegalArgumentException("severity cannot be empty");
        if (!VALID_SEVERITIES.contains(severity.toUpperCase()))
            throw new IllegalArgumentException("Invalid severity. Allowed: LOW, MEDIUM, HIGH, CRITICAL");
        return elasticsearchRepo.findBySeverity(severity.toUpperCase());
    }


    public List<EventDocument> getEventsBySource(String source) {
        if (source == null || source.isBlank())
            throw new IllegalArgumentException("source cannot be empty");
        if (!VALID_SOURCES.contains(source.toUpperCase()))
            throw new IllegalArgumentException("Invalid source. Allowed: NDR, EDR");
        return elasticsearchRepo.findBySource(source.toUpperCase());
    }


    public List<EventDocument> getEventsByType(String eventType) {
        if (eventType == null || eventType.isBlank())
            throw new IllegalArgumentException("eventType cannot be empty");
        return elasticsearchRepo.findByEventType(eventType);
    }

    public long getTotalEventCount() {
        return postgresRepo.count();
    }

    public List<EventRecord> getAllRecords() {
        return postgresRepo.findAll();
    }

    public Map<String, Object> getStats() {

        return Map.of(
                "totalEvents", postgresRepo.count(),
                "highSeverity", elasticsearchRepo.countBySeverity("HIGH"),
                "mediumSeverity", elasticsearchRepo.countBySeverity("MEDIUM"),
                "lowSeverity", elasticsearchRepo.countBySeverity("LOW"),
                "ndrEvents", elasticsearchRepo.countBySource("NDR"),
                "edrEvents", elasticsearchRepo.countBySource("EDR")
        );
    }
}
