package com.nibado.example.hc.eventsink;

import com.hazelcast.config.Config;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.*;
import com.nibado.example.hc.events.Event;
import com.nibado.example.hc.events.EventSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class EventService implements MessageListener<Event> {
    private static final long SECOND = 1000L;
    private static final long MINUTE = SECOND * 60;
    private static final long HOUR = MINUTE * 60;
    private static final long DAY = HOUR * 24;

    private final EventSender sender;
    private ITopic<Event> topic;

    @Autowired
    public EventService(final EventSender sender) {
        this.sender = sender;
    }

    @Override
    public void onMessage(Message<Event> message) {
        log.info("Got {} from {}", message.getMessageObject(), message.getPublishingMember());
        sender.send(message.getMessageObject());
    }


    @PostConstruct
    public void init() {
        SerializerConfig sc = new SerializerConfig().
                setImplementation(new EventSerializer()).
                setTypeClass(Event.class);

        Config config = new Config();
        config.getSerializationConfig().addSerializerConfig(sc);

        HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance(config);

        topic = hazelcast.getTopic("events");
        topic.addMessageListener(this);
    }
}
