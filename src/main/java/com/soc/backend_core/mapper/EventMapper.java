package com.soc.backend_core.mapper;

import com.soc.backend_core.Entities.domain.UnifiedEvent;
import com.soc.backend_core.Entities.elastic.EventDocument;
import com.soc.backend_core.Entities.jpa.EventRecord;
import com.soc.backend_core.dto.EndpointEventRequest;
import com.soc.backend_core.dto.LoginEventRequest;
import com.soc.backend_core.dto.NetworkEventRequest;
import org.mapstruct.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        imports = {UUID.class, Instant.class, HashMap.class, Map.class}
)
public interface EventMapper {

    // ─────────────────────────────────────────────
    // NetworkEventRequest → UnifiedEvent
    // ─────────────────────────────────────────────

    @Mapping(target = "eventId",       expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "timestamp",     expression = "java(Instant.now())")
    @Mapping(target = "severity",      constant = "MEDIUM")
    @Mapping(target = "source",        constant = "NDR")
    @Mapping(source = "deviceId",      target = "deviceId")
    @Mapping(source = "eventType",     target = "eventType")
    @Mapping(source = "srcIp",         target = "sourceIp")
    @Mapping(source = "destIp",        target = "destinationIp")
    @Mapping(target = "process",       ignore = true)
    @Mapping(target = "user",          ignore = true)
    @Mapping(target = "raw",           expression = "java(toRaw(dto))")
    UnifiedEvent fromNetwork(NetworkEventRequest dto);

    // ─────────────────────────────────────────────
    // EndpointEventRequest → UnifiedEvent
    // ─────────────────────────────────────────────

    @Mapping(target = "eventId",       expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "timestamp",     expression = "java(Instant.now())")
    @Mapping(target = "severity",      constant = "LOW")
    @Mapping(target = "source",        constant = "EDR")
    @Mapping(source = "deviceId",      target = "deviceId")
    @Mapping(source = "eventType",     target = "eventType")
    @Mapping(source = "process",       target = "process")
    @Mapping(source = "user",          target = "user")
    @Mapping(target = "sourceIp",      ignore = true)
    @Mapping(target = "destinationIp", ignore = true)
    @Mapping(source = "raw",           target = "raw")
    UnifiedEvent fromEndpoint(EndpointEventRequest dto);

    // ─────────────────────────────────────────────
    // LoginEventRequest → UnifiedEvent
    // ─────────────────────────────────────────────

    @Mapping(target = "eventId",       expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "timestamp",     expression = "java(Instant.now())")
    @Mapping(target = "eventType",     constant = "login_attempt")
    @Mapping(target = "source",        constant = "EDR")
    @Mapping(target = "severity",      expression = "java(dto.isFailed() ? \"HIGH\" : \"LOW\")")
    @Mapping(source = "deviceId",      target = "deviceId")
    @Mapping(source = "user",          target = "user")
    @Mapping(source = "sourceIp",      target = "sourceIp")
    @Mapping(target = "destinationIp", ignore = true)
    @Mapping(target = "process",       ignore = true)
    @Mapping(target = "raw",           expression = "java(toRawLogin(dto))")
    UnifiedEvent fromLogin(LoginEventRequest dto);

    // ─────────────────────────────────────────────
    // UnifiedEvent → Elasticsearch document
    // ─────────────────────────────────────────────

    EventDocument toDocument(UnifiedEvent event);

    // ─────────────────────────────────────────────
    // UnifiedEvent → PostgreSQL record
    // ─────────────────────────────────────────────

    EventRecord toRecord(UnifiedEvent event);

    // ─────────────────────────────────────────────
    // Helpers — build raw payload maps
    // ─────────────────────────────────────────────

    default Map<String, Object> toRaw(NetworkEventRequest dto) {
        Map<String, Object> raw = new HashMap<>();
        raw.put("deviceId",  dto.getDeviceId());
        raw.put("eventType", dto.getEventType());
        raw.put("srcIp",     dto.getSrcIp());
        raw.put("destIp",    dto.getDestIp());
        return raw;
    }

    default Map<String, Object> toRawLogin(LoginEventRequest dto) {
        Map<String, Object> raw = new HashMap<>();
        raw.put("deviceId",  dto.getDeviceId());
        raw.put("user",      dto.getUser());
        raw.put("sourceIp",  dto.getSourceIp());
        raw.put("failed",    dto.isFailed());
        return raw;
    }
}
