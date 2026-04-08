package com.soc.backend_core.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginEventRequest {

    @NotBlank
    private String deviceId;

    @NotBlank
    private String user;

    private String sourceIp;
    private boolean failed;
    public String getDeviceId() { return deviceId; }
    public String getUser() { return user; }
    public String getSourceIp() { return sourceIp; }
    public boolean isFailed() { return failed; }
}