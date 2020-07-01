package com.restsecure.components.deserialize;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class DefaultJacksonJsonDeserializer implements Deserializer {

    @Override
    public <T> T deserialize(String content, Class<T> tClass) {
        try {
            return new ObjectMapper().readValue(content, tClass);
        } catch (IOException e) {
            throw new DeserializeException(e);
        }
    }
}
