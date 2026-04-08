package com.soc.backend_core.repository;

import com.soc.backend_core.model.EventRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRecordRepository
        extends JpaRepository<EventRecord, String> {

    List<EventRecord> findByDeviceId(String deviceId);
    List<EventRecord> findBySeverity(String severity);
    List<EventRecord> findBySource(String source);
}