package com.nibado.example.hc.eventsource;

import com.nibado.example.hc.events.LogEvent;
import com.nibado.example.hc.events.LoginEvent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService service;

    @Autowired
    public EventController(final EventService service) {
        this.service = service;
    }

    @PostMapping("/login/{userId:.+}")
    public ResponseEntity<?> login(@PathVariable final String userId) {
        service.publish(new LoginEvent(userId));
        log.info("Published user login event for user {}", userId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/log")
    public ResponseEntity<?> log(@RequestBody final LogRequest request) {
        service.publish(new LogEvent(request.level, request.message));

        log.info("Published log event {} {}", request.level, request.message);

        return ResponseEntity.noContent().build();
    }

    @Data
    private static class LogRequest {
        private LogEvent.Level level;
        private String message;
    }
}
