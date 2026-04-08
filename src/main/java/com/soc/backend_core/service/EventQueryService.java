package com.soc.backend_core.service;

import com.soc.backend_core.model.EventDocument;
import com.soc.backend_core.model.EventRecord;
import com.soc.backend_core.repository.EventDocumentRepository;
import com.soc.backend_core.repository.EventRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<EventDocument> getAllEvents() {
        List<EventDocument> results = new ArrayList<>();
        elasticsearchRepo.findAll().forEach(results::add);
        return results;
    }

    public List<EventDocument> getEventsByDevice(String deviceId) {
        return elasticsearchRepo.findByDeviceId(deviceId);
    }

    public List<EventDocument> getEventsBySeverity(String severity) {
        return elasticsearchRepo.findBySeverity(severity);
    }

    public List<EventDocument> getEventsBySource(String source) {
        return elasticsearchRepo.findBySource(source);
    }

    public List<EventDocument> getEventsByType(String eventType) {
        return elasticsearchRepo.findByEventType(eventType);
    }

    public long getTotalEventCount() {
        return postgresRepo.count();
    }

    public List<EventRecord> getAllRecords() {
        return postgresRepo.findAll();
    }
}