package com.restsecure.gson;

import com.google.gson.Gson;
import com.restsecure.core.mapping.ObjectMapper;

import java.lang.reflect.Type;

public class GsonMapper implements ObjectMapper {

    private Gson mapper;

    public GsonMapper() {
        this.mapper = new Gson();
    }

    public GsonMapper(Gson gson) {
        this.mapper = gson;
    }

    @Override
    public <T> T deserialize(String content, Class<T> tClass) {
        return mapper.fromJson(content, tClass);
    }

    @Override
    public <T> T deserialize(String content, Type type) {
        return mapper.fromJson(content, type);
    }

    @Override
    public <T> String serialize(T object) {
        return mapper.toJson(object);
    }
}
