package com.restsecure.core.mapping.deserialize;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Type;

public class DefaultJacksonDeserializer implements Deserializer {

    private ObjectMapper mapper;

    public DefaultJacksonDeserializer() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public <T> T deserialize(String content, Class<T> tClass) {
        try {
            return mapper.readValue(content, tClass);
        } catch (IOException e) {
            throw new DeserializeException(e);
        }
    }

    @Override
    public <T> T deserialize(String content, Type type) {
        try {
            return mapper.readValue(content, mapper.constructType(type));
        } catch (IOException e) {
            throw new DeserializeException(e);
        }
    }
}
