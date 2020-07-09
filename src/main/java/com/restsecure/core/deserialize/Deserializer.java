package com.restsecure.core.deserialize;

public interface Deserializer {
    <T> T deserialize(String content, Class<T> tClass);
}
