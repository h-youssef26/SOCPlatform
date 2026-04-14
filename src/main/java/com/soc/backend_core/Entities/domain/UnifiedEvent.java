package com.soc.backend_core.Entities.domain;

import lombok.*;
import java.time.Instant;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnifiedEvent {
    private String eventId;
    private String deviceId;
    private String eventType;
    private String sourceIp;
    private String destinationIp;
    private String process;
    private String user;
    private String severity;
    private String source;
    private Instant timestamp;
    private Map<String, Object> raw;
}
