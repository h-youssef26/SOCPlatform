package com.soc.backend_core.repository.elastic;

import com.soc.backend_core.Entities.elastic.EventDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventDocumentRepository
        extends ElasticsearchRepository<EventDocument, String> {

    List<EventDocument> findByDeviceId(String deviceId);
    List<EventDocument> findBySeverity(String severity);
    List<EventDocument> findBySource(String source);
    List<EventDocument> findByEventType(String eventType);

    long countBySeverity(String severity);
    long countBySource(String source);
}
