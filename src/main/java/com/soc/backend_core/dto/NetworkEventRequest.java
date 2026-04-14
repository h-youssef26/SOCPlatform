package com.soc.backend_core.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import com.soc.backend_core.validation.ValidIp;


@Data
public class NetworkEventRequest {

    @NotBlank(message = "deviceId is required")
    private String deviceId;

    @NotBlank(message = "eventType is required")
    private String eventType;

    @ValidIp
    private String srcIp;

    @ValidIp
    private String destIp;
}
