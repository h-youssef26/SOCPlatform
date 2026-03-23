package com.SOC.BACKEND_CORE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.SOC.BACKEND_CORE.Model.SecurityEvent;
public interface SecurityEventRepository extends JpaRepository<SecurityEvent, String> {

}
