package com.restsecure.mapper;

public interface ResponseMapper {
    <T> T deserialize(String content, Class<T> tClass);
}
