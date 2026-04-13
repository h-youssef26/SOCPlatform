package com.soc.backend_core.Entities.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.util.Map;

@Document(indexName = "soc-events")
public class EventDocument {

    @Id
    private String eventId;

    @Field(type = FieldType.Keyword)
    private String deviceId;

    @Field(type = FieldType.Keyword)
    private String eventType;

    @Field(type = FieldType.Keyword)
    private String sourceIp;

    @Field(type = FieldType.Keyword)
    private String destinationIp;

    @Field(type = FieldType.Keyword)
    private String process;

    @Field(type = FieldType.Keyword)
    private String user;

    @Field(type = FieldType.Keyword)
    private String severity;

    @Field(type = FieldType.Keyword)
    private String source;

    @Field(type = FieldType.Date)
    private Instant timestamp;

    @Field(type = FieldType.Object)
    private Map<String, Object> raw;

    public EventDocument() {}

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

        public EventDocument build() {
            EventDocument d = new EventDocument();
            d.eventId = eventId; d.deviceId = deviceId;
            d.eventType = eventType; d.sourceIp = sourceIp;
            d.destinationIp = destinationIp; d.process = process;
            d.user = user; d.severity = severity;
            d.source = source; d.timestamp = timestamp; d.raw = raw;
            return d;
        }
    }
}
