package com.soc.backend_core.repository.jpa;

import com.soc.backend_core.Entities.jpa.SoarCommandRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SoarCommandRepository
        extends JpaRepository<SoarCommandRecord, String> {

    List<SoarCommandRecord> findByDeviceId(String deviceId);
    List<SoarCommandRecord> findByCommandType(String commandType);
}
