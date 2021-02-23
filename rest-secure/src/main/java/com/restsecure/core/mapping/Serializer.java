package com.restsecure.core.mapping;

public interface Serializer {
    <T> String serialize(T object);
}
