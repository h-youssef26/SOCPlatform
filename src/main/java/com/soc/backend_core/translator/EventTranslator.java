package com.soc.backend_core.translator;

import com.soc.backend_core.Entities.domain.UnifiedEvent;
import com.soc.backend_core.dto.EndpointEventRequest;
import com.soc.backend_core.dto.LoginEventRequest;
import com.soc.backend_core.dto.NetworkEventRequest;
import com.soc.backend_core.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EventTranslator {

    private final EventMapper mapper;

    public UnifiedEvent fromNetwork(NetworkEventRequest dto) {
        return mapper.fromNetwork(dto);
    }

    public UnifiedEvent fromEndpoint(EndpointEventRequest dto) {
        return mapper.fromEndpoint(dto);
    }

    public UnifiedEvent fromLogin(LoginEventRequest dto) {
        return mapper.fromLogin(dto);
    }
}
