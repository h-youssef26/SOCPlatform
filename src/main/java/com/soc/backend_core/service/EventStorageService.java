package com.soc.backend_core.service;

import com.soc.backend_core.model.EventDocument;
import com.soc.backend_core.model.EventRecord;
import com.soc.backend_core.model.UnifiedEvent;
import com.soc.backend_core.repository.EventDocumentRepository;
import com.soc.backend_core.repository.EventRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventStorageService {
    private static final Logger log = LoggerFactory.getLogger(EventStorageService.class);
    private final EventDocumentRepository elasticsearchRepo;
    private final EventRecordRepository postgresRepo;

    public void storeEvent(UnifiedEvent event) {
        saveToElasticsearch(event);
        saveToPostgres(event);
    }

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
            log.error("Failed to save to Elasticsearch: {}", e.getMessage());
        }
    }

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
            log.error("Failed to save to PostgreSQL: {}", e.getMessage());
        }
    }
}