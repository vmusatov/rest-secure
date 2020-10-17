package com.restsecure.core.apache;

import com.restsecure.core.configuration.Config;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClientBuilder;

public class ApacheConfig implements Config {
    @Getter
    @Setter
    private HttpClientContext httpClientContext;
    @Getter
    @Setter
    private HttpClientBuilder httpClientBuilder;

    public ApacheConfig() {
        reset();
    }

    @Override
    public void reset() {
        this.httpClientContext = HttpClientContext.create();
        this.httpClientBuilder = HttpClientBuilder.create();
    }
}
