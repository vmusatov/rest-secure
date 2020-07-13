package com.restsecure.core.mapping.deserialize;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class DefaultJacksonDeserializer implements Deserializer {

    @Override
    public <T> T deserialize(String content, Class<T> tClass) {
        try {
            return new ObjectMapper().readValue(content, tClass);
        } catch (IOException e) {
            throw new DeserializeException(e);
        }
    }
}
