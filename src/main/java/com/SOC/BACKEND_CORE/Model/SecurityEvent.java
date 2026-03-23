package com.SOC.BACKEND_CORE.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;


@Entity
@Table(name = "security_events")
@Getter
@Setter
@Data
public class SecurityEvent {
    @Id
    private String id;
    @Embedded
    private Event event;
    @Embedded
    private Network network;
    @Embedded
    private Device device;
    private Instant timestamp;
}
