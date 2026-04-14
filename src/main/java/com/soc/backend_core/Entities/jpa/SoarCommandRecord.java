package com.soc.backend_core.Entities.jpa;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "soar_commands")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SoarCommandRecord {

    @Id
    @Column(name = "command_id", nullable = false, updatable = false)
    private String commandId;

    @Column(name = "command_type", nullable = false)
    private String commandType;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(name = "target_process")
    private String targetProcess;

    @Column(name = "target_ip")
    private String targetIp;

    @Column(name = "triggered_by", nullable = false)
    private String triggeredBy;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}
