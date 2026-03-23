package com.SOC.BACKEND_CORE.Model;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Embeddable
public class Device {
    @Column(name = "device_id")
    private String id;
    private String type;
}
