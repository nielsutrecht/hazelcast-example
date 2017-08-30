package com.nibado.example.hc.eventsource;

import com.hazelcast.config.Config;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.*;
import com.nibado.example.hc.events.Event;
import com.nibado.example.hc.events.EventSerializer;
import com.nibado.example.hc.events.TimeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class EventService implements MessageListener<Event> {
    private static final long SECOND = 1000L;
    private static final long MINUTE = SECOND * 60;
    private static final long HOUR = MINUTE * 60;
    private static final long DAY = HOUR * 24;

    private ITopic<Event> topic;

    @Scheduled(fixedRate = SECOND)
    public void oncePerSecond() {
        publish(new TimeEvent(TimeEvent.Interval.SECOND));
    }

    @Scheduled(fixedRate = MINUTE)
    public void oncePerMinute() {
        publish(new TimeEvent(TimeEvent.Interval.MINUTE));
    }

    @Scheduled(fixedRate = HOUR)
    public void oncePerHour() {
        publish(new TimeEvent(TimeEvent.Interval.HOURLY));
    }

    @Scheduled(fixedRate = DAY)
    public void oncePerDay() {
        publish(new TimeEvent(TimeEvent.Interval.DAILY));
    }

    @Override
    public void onMessage(Message<Event> message) {
        log.info("Got {} from {}", message.getMessageObject(), message.getPublishingMember());
    }

    public void publish(final Event event) {
        topic.publish(event);
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
