// AlertMessage.java
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
public class AlertMessage {
    private String alertId;
    private String type;
    private String severity;
    private String deviceId;
    private String message;
    private String commandType;
    private Instant timestamp;
}
