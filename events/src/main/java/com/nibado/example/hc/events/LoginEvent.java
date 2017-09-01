package com.nibado.example.hc.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class LoginEvent extends Event {
    private final String userId;

    public LoginEvent(String userId) {
        this(userId, ZonedDateTime.now());
    }

    @JsonCreator
    public LoginEvent(@JsonProperty("userId") final String userId, @JsonProperty("time") final ZonedDateTime time) {
        super("login", time);
        this.userId = userId;
    }
}
