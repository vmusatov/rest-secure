package com.restsecure.core.response;

import com.jayway.jsonpath.JsonPath;
import com.restsecure.core.deserialize.DefaultJacksonJsonDeserializer;
import com.restsecure.core.deserialize.Deserializer;

public class ResponseBody {
    private final String content;
    private final Deserializer deserializer;

    public ResponseBody(String content) {
        this.content = content;
        this.deserializer = new DefaultJacksonJsonDeserializer();
    }

    public ResponseBody(String content, Deserializer deserializer) {
        this.content = content;
        this.deserializer = deserializer;
    }

    public String getContent() {
        return content;
    }

    public <T> T as(Class<T> to) {
        return this.deserializer.deserialize(content, to);
    }

    public <T> T get(String path) {
        return JsonPath.read(content, path);
    }
}
