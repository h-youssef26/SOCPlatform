package com.soc.backend_core.dto;

public class SoarCommandRequest {

    private String deviceId;
    private String targetProcess;
    private String targetIp;
    private String triggeredBy;

    public SoarCommandRequest() {}

    public String getDeviceId() { return deviceId; }
    public String getTargetProcess() { return targetProcess; }
    public String getTargetIp() { return targetIp; }
    public String getTriggeredBy() { return triggeredBy; }
}