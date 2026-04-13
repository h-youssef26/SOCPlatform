package com.soc.backend_core.service;

import com.soc.backend_core.Entities.elastic.EventDocument;
import com.soc.backend_core.Entities.jpa.EventRecord;
import com.soc.backend_core.repository.elastic.EventDocumentRepository;
import com.soc.backend_core.repository.jpa.EventRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Provides query APIs for retrieving stored security events
 * from Elasticsearch and PostgreSQL.
 */

@Service
public class EventQueryService {

    private static final Logger log = LoggerFactory.getLogger(EventQueryService.class);

    private final EventDocumentRepository elasticsearchRepo;
    private final EventRecordRepository postgresRepo;

    public EventQueryService(EventDocumentRepository elasticsearchRepo,
                             EventRecordRepository postgresRepo) {
        this.elasticsearchRepo = elasticsearchRepo;
        this.postgresRepo = postgresRepo;
    }


    /**
     * Retrieves all events from Elasticsearch.
     */

    public List<EventDocument> getAllEvents() {
        List<EventDocument> results = new ArrayList<>();
        elasticsearchRepo.findAll().forEach(results::add);
        return results;
    }

    /**
     * Retrieves events filtered by device ID.
     */

    public List<EventDocument> getEventsByDevice(String deviceId) {
        return elasticsearchRepo.findByDeviceId(deviceId);
    }


    /**
     * Retrieves events filtered by severity level.
     */

    public List<EventDocument> getEventsBySeverity(String severity) {
        return elasticsearchRepo.findBySeverity(severity);
    }

    /**
     * Retrieves events filtered by source system.
     */

    public List<EventDocument> getEventsBySource(String source) {
        return elasticsearchRepo.findBySource(source);
    }

    /**
     * Retrieves events filtered by event type.
     */

    public List<EventDocument> getEventsByType(String eventType) {
        return elasticsearchRepo.findByEventType(eventType);
    }

    /**
     * Returns total number of stored events in PostgreSQL.
     */

    public long getTotalEventCount() {
        return postgresRepo.count();
    }

    public List<EventRecord> getAllRecords() {
        return postgresRepo.findAll();
    }
}
