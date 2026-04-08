package com.soc.backend_core.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Map;

@Data
public class EndpointEventRequest {

    @NotBlank
    private String deviceId;

    @NotBlank
    private String eventType;

    private String process;
    private String user;
    private Map<String, Object> raw;

    public String getDeviceId() { return deviceId; }
    public String getEventType() { return eventType; }
    public String getProcess() { return process; }
    public String getUser() { return user; }
    public java.util.Map<String, Object> getRaw() { return raw; }
}