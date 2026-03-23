package com.SOC.BACKEND_CORE.Model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Network {
    private String source_ip;
    private String destination_ip;
    private String protocol;
}
