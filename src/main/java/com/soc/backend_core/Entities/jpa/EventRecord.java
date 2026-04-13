package com.soc.backend_core.Entities.jpa;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "events")
public class EventRecord {

    @Id
    private String eventId;

    @Column(nullable = false)
    private String deviceId;

    @Column(nullable = false)
    private String eventType;

    private String sourceIp;
    private String destinationIp;
    private String process;

    @Column(name = "username")
    private String user;

    @Column(nullable = false)
    private String severity;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private Instant timestamp;

    public EventRecord() {}

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

    public static class Builder {
        private String eventId, deviceId, eventType, sourceIp;
        private String destinationIp, process, user, severity, source;
        private Instant timestamp;

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

        public EventRecord build() {
            EventRecord r = new EventRecord();
            r.eventId = eventId; r.deviceId = deviceId;
            r.eventType = eventType; r.sourceIp = sourceIp;
            r.destinationIp = destinationIp; r.process = process;
            r.user = user; r.severity = severity;
            r.source = source; r.timestamp = timestamp;
            return r;
        }
    }
}
