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
}