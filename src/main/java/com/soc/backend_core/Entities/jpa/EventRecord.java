package com.soc.backend_core.Entities.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class EventRecord {

    @Id
    private String eventId;

    @Column(nullable = false)
    @NotBlank
    private String deviceId;

    @Column(nullable = false)
    private String eventType;

    private String sourceIp;
    private String destinationIp;
    private String process;

    @Column(name = "username")
    private String user;

    @Column(nullable = false)
    private String severity;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private Instant timestamp;
}
