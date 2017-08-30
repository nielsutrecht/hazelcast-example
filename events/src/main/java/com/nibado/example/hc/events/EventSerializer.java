package com.nibado.example.hc.events;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class EventSerializer implements StreamSerializer<Event> {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        SimpleModule module = new SimpleModule();

        module.addSerializer(ZonedDateTime.class, new JsonSerializer<ZonedDateTime>() {
            @Override
            public void serialize(ZonedDateTime zonedDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString(zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
            }
        });
        module.addDeserializer(ZonedDateTime.class, new JsonDeserializer<ZonedDateTime>() {
            @Override
            public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                return ZonedDateTime.parse(jsonParser.getValueAsString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            }
        });
        MAPPER.registerModule(module);
    }

    @Override
    public void write(ObjectDataOutput objectDataOutput, Event event) throws IOException {
        objectDataOutput.writeUTF(toString(event));
    }

    @Override
    public Event read(ObjectDataInput objectDataInput) throws IOException {
        return fromString(objectDataInput.readUTF());
    }

    String toString(final Event event) {
        try {
            return MAPPER.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    Event fromString(final String json) {
        try {
            return MAPPER.readValue(json, Event.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getTypeId() {
        return 1;
    }

    @Override
    public void destroy() {
    }
}
