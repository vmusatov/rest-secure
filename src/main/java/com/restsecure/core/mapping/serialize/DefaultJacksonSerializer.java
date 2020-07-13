package com.restsecure.core.mapping.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class DefaultJacksonSerializer implements Serializer {
    @Override
    public <T> String serialize(T object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (IOException e) {
            throw new SerializeException(e);
        }
    }
}
