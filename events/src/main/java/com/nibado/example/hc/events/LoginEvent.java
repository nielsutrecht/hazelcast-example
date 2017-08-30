package com.nibado.example.hc.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class LoginEvent extends Event {
    private final UUID userId;

    public LoginEvent(UUID userId) {
        this(userId, ZonedDateTime.now());
    }

    @JsonCreator
    public LoginEvent(@JsonProperty("userId") final UUID userId, @JsonProperty("time") final ZonedDateTime time) {
        super("login", time);
        this.userId = userId;
    }
}
