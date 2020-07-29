package com.restsecure.core.response;

import com.jayway.jsonpath.JsonPath;
import com.restsecure.core.exception.RestSecureException;
import com.restsecure.core.mapping.deserialize.DefaultJacksonDeserializer;
import com.restsecure.core.mapping.deserialize.Deserializer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;

public class ResponseBody {
    private final String content;
    private final Deserializer deserializer;

    public ResponseBody(String content) {
        this.content = content;
        this.deserializer = new DefaultJacksonDeserializer();
    }

    public ResponseBody(String content, Deserializer deserializer) {
        this.content = content;
        this.deserializer = deserializer;
    }

    public String asString() {
        return content;
    }

    public byte[] asByteArray() {
        checkContentNotNull();
        return content.getBytes();
    }

    public InputStream asInputStream() {
        checkContentNotNull();
        return new ByteArrayInputStream(content.getBytes());
    }

    public <T> T as(Class<T> to) {
        return this.deserializer.deserialize(asString(), to);
    }

    public <T> T as(Type to) {
        return this.deserializer.deserialize(asString(), to);
    }

    public <T> T get(String path) {
        return JsonPath.read(asString(), path);
    }

    private void checkContentNotNull() {
        if (content == null) {
            throw new RestSecureException("Content is null");
        }
    }
}
