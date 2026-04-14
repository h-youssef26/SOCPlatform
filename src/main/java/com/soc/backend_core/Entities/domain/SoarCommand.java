package com.soc.backend_core.Entities.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SoarCommand {
    private String commandId;
    private String commandType;
    private String deviceId;
    private String targetProcess;
    private String targetIp;
    private String triggeredBy;
    private String status;
    private Instant createdAt;
}
