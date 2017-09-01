package com.nibado.example.hc.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class LogEvent extends Event {
    private final Level level;
    private final String message;

    @JsonCreator
    public LogEvent(@JsonProperty("level") final Level level, @JsonProperty("message") String message, @JsonProperty("time") final ZonedDateTime time) {
        super("log", time);
        this.level = level;
        this.message = message;
    }

    public LogEvent(final Level level, final String message) {
        this(level, message, ZonedDateTime.now());
    }

    public enum Level {
        DEBUG,
        INFO,
        WARN,
        ERROR
    }
}
