package com.soc.backend_core.model;

import java.time.Instant;

public class SoarCommand {

    private String commandId;
    private String commandType;   // KILL_PROCESS, BLOCK_IP, ISOLATE_HOST
    private String deviceId;      // which machine to execute on
    private String targetProcess; // for KILL_PROCESS
    private String targetIp;      // for BLOCK_IP
    private String triggeredBy;   // what event triggered this
    private String status;        // PENDING, SENT, SUCCESS, FAILED
    private Instant createdAt;

    public SoarCommand() {}

    public static Builder builder() { return new Builder(); }

    public String getCommandId() { return commandId; }
    public String getCommandType() { return commandType; }
    public String getDeviceId() { return deviceId; }
    public String getTargetProcess() { return targetProcess; }
    public String getTargetIp() { return targetIp; }
    public String getTriggeredBy() { return triggeredBy; }
    public String getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public void setStatus(String status) { this.status = status; }

    public static class Builder {
        private String commandId;
        private String commandType;
        private String deviceId;
        private String targetProcess;
        private String targetIp;
        private String triggeredBy;
        private String status;
        private Instant createdAt;

        public Builder commandId(String v) { this.commandId = v; return this; }
        public Builder commandType(String v) { this.commandType = v; return this; }
        public Builder deviceId(String v) { this.deviceId = v; return this; }
        public Builder targetProcess(String v) { this.targetProcess = v; return this; }
        public Builder targetIp(String v) { this.targetIp = v; return this; }
        public Builder triggeredBy(String v) { this.triggeredBy = v; return this; }
        public Builder status(String v) { this.status = v; return this; }
        public Builder createdAt(Instant v) { this.createdAt = v; return this; }

        public SoarCommand build() {
            SoarCommand c = new SoarCommand();
            c.commandId = commandId;
            c.commandType = commandType;
            c.deviceId = deviceId;
            c.targetProcess = targetProcess;
            c.targetIp = targetIp;
            c.triggeredBy = triggeredBy;
            c.status = status;
            c.createdAt = createdAt;
            return c;
        }
    }
}