package com.nibado.example.hc.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "key")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TimeEvent.class, name = "time"),
        @JsonSubTypes.Type(value = LoginEvent.class, name = "login")
})
public abstract class Event {
    private final String key;
    private final ZonedDateTime time;

    public Event(final String key) {
        this(key, ZonedDateTime.now());
    }
}
