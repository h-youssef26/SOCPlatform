package com.soc.backend_core.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Map;

@Data
public class NetworkEventRequest {

    @NotBlank
    private String deviceId;

    @NotBlank
    private String eventType;

    private String srcIp;
    private String destIp;
    private Map<String, Object> raw;
    public String getDeviceId() { return deviceId; }
public String getEventType() { return eventType; }
public String getSrcIp() { return srcIp; }
public String getDestIp() { return destIp; }
public java.util.Map<String, Object> getRaw() { return raw; }
}