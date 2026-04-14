package com.soc.backend_core.service;

import com.soc.backend_core.Entities.elastic.EventDocument;
import com.soc.backend_core.Entities.jpa.EventRecord;
import com.soc.backend_core.Entities.domain.UnifiedEvent;
import com.soc.backend_core.mapper.EventMapper;
import com.soc.backend_core.repository.elastic.EventDocumentRepository;
import com.soc.backend_core.repository.jpa.EventRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * Stores incoming security events into:
 * - Elasticsearch (for search and analytics)
 * - PostgreSQL (for persistent storage)
 */

@Service
@RequiredArgsConstructor
public class EventStorageService {

    private final EventDocumentRepository elasticRepo;
    private final EventRecordRepository postgresRepo;
    private final EventMapper mapper;

    public void storeEvent(UnifiedEvent event) {
        if (event == null)
            throw new IllegalArgumentException("Event cannot be null");
        if (event.getEventId() == null || event.getEventId().isBlank())
            throw new IllegalArgumentException("Event must have a valid eventId");

        elasticRepo.save(mapper.toDocument(event));
        postgresRepo.save(mapper.toRecord(event));
    }
}
