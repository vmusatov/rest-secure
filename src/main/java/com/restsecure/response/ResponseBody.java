package com.restsecure.response;

import com.jayway.jsonpath.JsonPath;
import com.restsecure.RestSecureConfiguration;

public class ResponseBody {
    private final String content;

    public ResponseBody(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public <T> T as(Class<T> to) {
        return RestSecureConfiguration.getMapper().deserialize(content, to);
    }

    public <T> T get(String path) {
        return JsonPath.read(content, path);
    }
}
