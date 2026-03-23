package com.SOC.BACKEND_CORE.Controller;

import com.SOC.BACKEND_CORE.DTO.NdrLogRequest;
import com.SOC.BACKEND_CORE.Service.EventService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class NetworkController {

    private final EventService eventService;

    public NetworkController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/network")
    public String receiveNetworkEvent(@RequestBody NdrLogRequest request) {

        eventService.processNetworkEvent(request);

        return "Event sent to Kafka";
    }
}
