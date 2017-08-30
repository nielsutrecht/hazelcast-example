package com.nibado.example.hc.eventsink;

import com.nibado.example.hc.events.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventSender {
    private SimpMessagingTemplate broker;

    @Autowired
    public EventSender(final SimpMessagingTemplate broker) {
        this.broker = broker;
    }

    public void send(final Event event) {
        broker.convertAndSend("/topic/events", event);
    }
}
