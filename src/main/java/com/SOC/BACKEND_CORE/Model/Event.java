package com.SOC.BACKEND_CORE.Model;


import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Event {
    private String category;
}
