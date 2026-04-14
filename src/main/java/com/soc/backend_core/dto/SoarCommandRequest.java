package com.soc.backend_core.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import com.soc.backend_core.validation.ValidIp;
import com.soc.backend_core.validation.ValidSoarCommand;

@ValidSoarCommand
@Data
public class SoarCommandRequest {

    @NotBlank(message = "deviceId is required")
    private String deviceId;

    private String targetProcess;

    @ValidIp
    private String targetIp;

    @NotBlank(message = "triggeredBy is required")
    private String triggeredBy;

}
