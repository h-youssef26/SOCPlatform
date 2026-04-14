package com.soc.backend_core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.soc.backend_core.validation.ValidIp;


@Data
public class LoginEventRequest {

    @NotBlank(message = "deviceId is required")
    private String deviceId;

    @NotBlank(message = "user is required")
    private String user;

    @ValidIp
    private String sourceIp;

    private boolean failed;
}
