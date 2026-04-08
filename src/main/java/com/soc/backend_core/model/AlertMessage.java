package com.soc.backend_core.model;

import java.time.Instant;

public class AlertMessage {

    private String alertId;
    private String type;        // ATTACK_DETECTED, COMMAND_EXECUTED
    private String severity;    // LOW, MEDIUM, HIGH
    private String deviceId;
    private String message;
    private String commandType; // what action was taken
    private Instant timestamp;

    public AlertMessage() {}

    public static Builder builder() { return new Builder(); }

    public String getAlertId() { return alertId; }
    public String getType() { return type; }
    public String getSeverity() { return severity; }
    public String getDeviceId() { return deviceId; }
    public String getMessage() { return message; }
    public String getCommandType() { return commandType; }
    public Instant getTimestamp() { return timestamp; }

    public static class Builder {
        private String alertId;
        private String type;
        private String severity;
        private String deviceId;
        private String message;
        private String commandType;
        private Instant timestamp;

        public Builder alertId(String v) { this.alertId = v; return this; }
        public Builder type(String v) { this.type = v; return this; }
        public Builder severity(String v) { this.severity = v; return this; }
        public Builder deviceId(String v) { this.deviceId = v; return this; }
        public Builder message(String v) { this.message = v; return this; }
        public Builder commandType(String v) { this.commandType = v; return this; }
        public Builder timestamp(Instant v) { this.timestamp = v; return this; }

        public AlertMessage build() {
            AlertMessage a = new AlertMessage();
            a.alertId = alertId;
            a.type = type;
            a.severity = severity;
            a.deviceId = deviceId;
            a.message = message;
            a.commandType = commandType;
            a.timestamp = timestamp;
            return a;
        }
    }
}