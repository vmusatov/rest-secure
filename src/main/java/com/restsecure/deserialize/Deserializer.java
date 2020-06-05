package com.restsecure.deserialize;

public interface Deserializer {
    <T> T deserialize(String content, Class<T> tClass);
}
