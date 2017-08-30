package com.nibado.example.hc.eventsource;

import com.nibado.example.hc.events.LoginEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService service;

    @Autowired
    public EventController(final EventService service) {
        this.service = service;
    }

    @PostMapping("/login/{userId}")
    public ResponseEntity<?> login(@PathVariable final UUID userId) {
        service.publish(new LoginEvent(userId));
        log.info("Published user login event for user {}", userId);

        return ResponseEntity.noContent().build();
    }
}