package com.soc.backend_core.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Map;

import jakarta.validation.constraints.NotNull;


@Data
public class EndpointEventRequest {

    @NotBlank(message = "deviceId is required")
    private String deviceId;

    @NotBlank(message = "eventType is required")
    private String eventType;

    @NotBlank(message = "process is required")
    private String process;

    @NotBlank(message = "user is required")
    private String user;

    @NotNull(message = "raw payload is required")
    private Map<String, Object> raw;

}
