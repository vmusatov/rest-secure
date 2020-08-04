package com.restsecure.core.mapping.serialize;

public interface Serializer {
    <T> String serialize(T object);
}
