package com.restsecure.core.configuration.configs;

import com.restsecure.core.configuration.BaseConfig;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClientBuilderConfig extends BaseConfig<HttpClientBuilder> {
    @Override
    public void initDefault() {
        value = HttpClientBuilder.create();
    }
}
