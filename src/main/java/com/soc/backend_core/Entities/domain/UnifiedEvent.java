package com.soc.backend_core.Entities.domain;

import java.time.Instant;
import java.util.Map;

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

    public UnifiedEvent() {}

    public UnifiedEvent(String eventId, String deviceId, String eventType,
                        String sourceIp, String destinationIp, String process,
                        String user, String severity, String source,
                        Instant timestamp, Map<String, Object> raw) {
        this.eventId = eventId;
        this.deviceId = deviceId;
        this.eventType = eventType;
        this.sourceIp = sourceIp;
        this.destinationIp = destinationIp;
        this.process = process;
        this.user = user;
        this.severity = severity;
        this.source = source;
        this.timestamp = timestamp;
        this.raw = raw;
    }

    public static Builder builder() { return new Builder(); }

    public String getEventId() { return eventId; }
    public String getDeviceId() { return deviceId; }
    public String getEventType() { return eventType; }
    public String getSourceIp() { return sourceIp; }
    public String getDestinationIp() { return destinationIp; }
    public String getProcess() { return process; }
    public String getUser() { return user; }
    public String getSeverity() { return severity; }
    public String getSource() { return source; }
    public Instant getTimestamp() { return timestamp; }
    public Map<String, Object> getRaw() { return raw; }

    public static class Builder {
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

        public Builder eventId(String v) { this.eventId = v; return this; }
        public Builder deviceId(String v) { this.deviceId = v; return this; }
        public Builder eventType(String v) { this.eventType = v; return this; }
        public Builder sourceIp(String v) { this.sourceIp = v; return this; }
        public Builder destinationIp(String v) { this.destinationIp = v; return this; }
        public Builder process(String v) { this.process = v; return this; }
        public Builder user(String v) { this.user = v; return this; }
        public Builder severity(String v) { this.severity = v; return this; }
        public Builder source(String v) { this.source = v; return this; }
        public Builder timestamp(Instant v) { this.timestamp = v; return this; }
        public Builder raw(Map<String, Object> v) { this.raw = v; return this; }

        public UnifiedEvent build() {
            return new UnifiedEvent(eventId, deviceId, eventType, sourceIp,
                    destinationIp, process, user, severity, source, timestamp, raw);
        }
    }
}
