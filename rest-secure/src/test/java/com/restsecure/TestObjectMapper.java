package com.restsecure;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.restsecure.core.exception.RestSecureException;
import com.restsecure.core.mapping.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Type;

public class TestObjectMapper implements ObjectMapper {

    private com.fasterxml.jackson.databind.ObjectMapper mapper;

    public TestObjectMapper() {
        this.mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
    }

    public TestObjectMapper(com.fasterxml.jackson.databind.ObjectMapper objectMapper) {
        this.mapper = objectMapper;
    }

    @Override
    public <T> T deserialize(String content, Class<T> tClass) {
        try {
            return mapper.readValue(content, tClass);
        } catch (IOException e) {
            throw new RestSecureException(e);
        }
    }

    @Override
    public <T> T deserialize(String content, Type type) {
        try {
            return mapper.readValue(content, mapper.constructType(type));
        } catch (IOException e) {
            throw new RestSecureException(e);
        }
    }

    @Override
    public <T> String serialize(T object) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(object);
        } catch (IOException e) {
            throw new RestSecureException(e);
        }
    }
}
