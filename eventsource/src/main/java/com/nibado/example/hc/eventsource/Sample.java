package com.nibado.example.hc.eventsource;

import com.hazelcast.config.Config;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.*;
import com.nibado.example.hc.events.Event;
import com.nibado.example.hc.events.EventSerializer;
import com.nibado.example.hc.events.TimeEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Sample implements MessageListener<Event> {
    public static void main(String... argv) throws Exception {
        SerializerConfig sc = new SerializerConfig().
                setImplementation(new EventSerializer()).
                setTypeClass(Event.class);

        Config config = new Config();
        config.getSerializationConfig().addSerializerConfig(sc);

        HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance(config);

        Sample sample = new Sample();

        ITopic<Event> topic = hazelcast.getTopic ("events");
        topic.addMessageListener(sample);
        topic.publish (new TimeEvent(TimeEvent.Interval.MINUTE));

        Thread.sleep(1000);
        System.exit(0);
    }

    @Override
    public void onMessage(Message<Event> message) {
        log.info("Message: {}", message.getMessageObject());
    }
}
