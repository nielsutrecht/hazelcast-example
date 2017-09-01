package com.nibado.example.hc.events;

import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class EventSerializerTest {
    @Test
    public void timeEvent() {
        EventSerializer serializer = new EventSerializer();
        TimeEvent event = new TimeEvent(TimeEvent.Interval.MINUTE);

        String data = serializer.toString(event);

        TimeEvent other = (TimeEvent) serializer.fromString(data);

        assertThat(other.getKey()).isEqualTo(event.getKey());
        assertThat(other.getTime()).isEqualTo(event.getTime());
        assertThat(other.getInterval()).isEqualTo(event.getInterval());
    }

    @Test
    public void loginEvent() {
        EventSerializer serializer = new EventSerializer();
        LoginEvent event = new LoginEvent(new UUID(0, 0));

        String data = serializer.toString(event);

        LoginEvent other = (LoginEvent) serializer.fromString(data);

        assertThat(other.getKey()).isEqualTo(event.getKey());
        assertThat(other.getTime()).isEqualTo(event.getTime());
        assertThat(other.getUserId()).isEqualTo(event.getUserId());
    }

    @Test
    public void logEvent() {
        EventSerializer serializer = new EventSerializer();
        LogEvent event = new LogEvent(LogEvent.Level.ERROR, "Could not connect");

        String data = serializer.toString(event);

        LogEvent other = (LogEvent) serializer.fromString(data);

        assertThat(other.getKey()).isEqualTo(event.getKey());
        assertThat(other.getTime()).isEqualTo(event.getTime());
        assertThat(other.getLevel()).isEqualTo(LogEvent.Level.ERROR);
        assertThat(other.getMessage()).isEqualTo("Could not connect");
    }
}
