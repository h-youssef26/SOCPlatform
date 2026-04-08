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
}