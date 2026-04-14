package com.soc.backend_core.mapper;

import com.soc.backend_core.Entities.domain.SoarCommand;
import com.soc.backend_core.Entities.jpa.SoarCommandRecord;
import org.springframework.stereotype.Component;

@Component
public class SoarCommandMapper {

    public SoarCommandRecord toRecord(SoarCommand cmd) {
        return SoarCommandRecord.builder()
                .commandId(cmd.getCommandId())
                .commandType(cmd.getCommandType())
                .deviceId(cmd.getDeviceId())
                .targetProcess(cmd.getTargetProcess())
                .targetIp(cmd.getTargetIp())
                .triggeredBy(cmd.getTriggeredBy())
                .status(cmd.getStatus())
                .createdAt(cmd.getCreatedAt())
                .build();
    }

    public SoarCommand toDomain(SoarCommandRecord rec) {
        return SoarCommand.builder()
                .commandId(rec.getCommandId())
                .commandType(rec.getCommandType())
                .deviceId(rec.getDeviceId())
                .targetProcess(rec.getTargetProcess())
                .targetIp(rec.getTargetIp())
                .triggeredBy(rec.getTriggeredBy())
                .status(rec.getStatus())
                .createdAt(rec.getCreatedAt())
                .build();
    }
}
