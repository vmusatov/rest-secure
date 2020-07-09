package com.restsecure.core.request;

import com.restsecure.core.configuration.Config;
import com.restsecure.core.configuration.ConfigFactory;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.response.Response;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.List;

public class RequestContext {
    @Getter
    @Setter
    private RequestSpecification specification;
    private final List<Config> configs;
    @Getter
    private final HttpClientContext httpClientContext;
    @Getter
    private final HttpClientBuilder httpClientBuilder;
    @Getter
    @Setter
    private Response response;

    public RequestContext(RequestSpecification specification) {
        this.specification = specification;
        this.configs = specification.getConfigs();
        this.httpClientContext = HttpClientContext.create();
        this.httpClientBuilder = HttpClientBuilder.create();
    }

    public <T extends Config> T getConfig(Class<T> configClass) {
        T config = ConfigFactory.getConfig(this.configs, configClass);

        if (config == null) {
            config = ConfigFactory.createDefaultConfig(configClass);
            this.configs.add(config);
        }

        return config;
    }
}
