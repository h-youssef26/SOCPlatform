package com.SOC.BACKEND_CORE.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NdrLogRequest {

    private String sensor;
    private String src_ip;
    private String dst_ip;
    private String protocol;
    private String alert_type;
    private String time_detected;

}
