package com.restsecure.response;

import com.jayway.jsonpath.JsonPath;
import com.restsecure.components.configuration.ConfigFactory;
import com.restsecure.components.deserialize.DeserializeConfig;

public class ResponseBody {
    private final String content;
    private final DeserializeConfig config;

    public ResponseBody(String content) {
        this.content = content;
        this.config = ConfigFactory.createDefaultConfig(DeserializeConfig.class);
    }

    public ResponseBody(String content, DeserializeConfig config) {
        this.content = content;
        this.config = config;
    }

    public String getContent() {
        return content;
    }

    public <T> T as(Class<T> to) {
        return this.config.getDeserializer().deserialize(content, to);
    }

    public <T> T get(String path) {
        return JsonPath.read(content, path);
    }
}
