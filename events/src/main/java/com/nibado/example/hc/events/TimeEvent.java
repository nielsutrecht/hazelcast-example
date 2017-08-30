package com.nibado.example.hc.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class TimeEvent extends Event {
    private final Interval interval;

    @JsonCreator
    public TimeEvent(@JsonProperty("interval") final Interval interval, @JsonProperty("time") final ZonedDateTime time) {
        super("time", time);
        this.interval = interval;
    }

    public TimeEvent(final Interval interval) {
        super("time");
        this.interval = interval;
    }

    public enum Interval {
        SECOND,
        MINUTE,
        HOURLY,
        DAILY,
        WEEKLY,
        MONTHLY
    }
}
