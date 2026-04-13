package com.soc.backend_core.service;

import com.soc.backend_core.Entities.elastic.EventDocument;
import com.soc.backend_core.Entities.jpa.EventRecord;
import com.soc.backend_core.Entities.domain.UnifiedEvent;
import com.soc.backend_core.repository.elastic.EventDocumentRepository;
import com.soc.backend_core.repository.jpa.EventRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * Stores incoming security events into:
 * - Elasticsearch (for search and analytics)
 * - PostgreSQL (for persistent storage)
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class EventStorageService {

    private final EventDocumentRepository elasticsearchRepo;
    private final EventRecordRepository postgresRepo;

    /**
     * Stores incoming event into both Elasticsearch and PostgreSQL.
     *
     * @param event unified security event
     */

    public void storeEvent(UnifiedEvent event) {
        if (event == null) {
            log.error("storeEvent called with null event");
            return;
        }

        log.info("Storing event: {}", event.getEventId());
        saveToElasticsearch(event);
        saveToPostgres(event);
    }

    /**
     * Saves event into Elasticsearch for fast searching and analytics.
     *
     * @param event unified security event
     */

    private void saveToElasticsearch(UnifiedEvent event) {
        try {
            EventDocument doc = EventDocument.builder()
                    .eventId(event.getEventId())
                    .deviceId(event.getDeviceId())
                    .eventType(event.getEventType())
                    .sourceIp(event.getSourceIp())
                    .destinationIp(event.getDestinationIp())
                    .process(event.getProcess())
                    .user(event.getUser())
                    .severity(event.getSeverity())
                    .source(event.getSource())
                    .timestamp(event.getTimestamp())
                    .raw(event.getRaw())
                    .build();

            elasticsearchRepo.save(doc);
            log.info("Event saved to Elasticsearch: {}", event.getEventId());

        } catch (Exception e) {
            log.error("Failed to save to Elasticsearch", e);
        }
    }


    /**
     * Saves event into PostgreSQL for persistent storage.
     *
     * @param event unified security event
     */

    private void saveToPostgres(UnifiedEvent event) {
        try {
            EventRecord record = EventRecord.builder()
                    .eventId(event.getEventId())
                    .deviceId(event.getDeviceId())
                    .eventType(event.getEventType())
                    .sourceIp(event.getSourceIp())
                    .destinationIp(event.getDestinationIp())
                    .process(event.getProcess())
                    .user(event.getUser())
                    .severity(event.getSeverity())
                    .source(event.getSource())
                    .timestamp(event.getTimestamp())
                    .build();

            postgresRepo.save(record);
            log.info("Event saved to PostgreSQL: {}", event.getEventId());

        } catch (Exception e) {
            log.error("Failed to save to PostgreSQL", e);
        }
    }
}
