package com.restsecure.deserialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restsecure.exception.ObjectMappingException;

import java.io.IOException;

public class DefaultJacksonJsonDeserializer implements Deserializer {

    @Override
    public <T> T deserialize(String content, Class<T> tClass) {
        try {
            return new ObjectMapper().readValue(content, tClass);
        } catch (IOException e) {
          throw new ObjectMappingException(e.getMessage());
        }
    }
}
