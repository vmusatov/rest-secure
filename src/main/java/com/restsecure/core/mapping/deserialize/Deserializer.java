package com.restsecure.core.mapping.deserialize;

public interface Deserializer {
    <T> T deserialize(String content, Class<T> tClass);
}
