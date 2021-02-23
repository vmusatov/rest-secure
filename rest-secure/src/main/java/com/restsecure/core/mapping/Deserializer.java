package com.restsecure.core.mapping;

import java.lang.reflect.Type;

public interface Deserializer {
    <T> T deserialize(String content, Class<T> tClass);

    <T> T deserialize(String content, Type type);
}
