package com.restsecure.core.configuration.configs;

import com.restsecure.core.configuration.BaseConfig;
import org.apache.http.client.protocol.HttpClientContext;

public class HttpClientContextConfig extends BaseConfig<HttpClientContext> {
    @Override
    public void initDefault() {
        value = HttpClientContext.create();
    }
}
