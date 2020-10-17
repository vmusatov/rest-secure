package com.restsecure.core.request;

import com.restsecure.core.configuration.Config;
import com.restsecure.core.configuration.ConfigFactory;
import com.restsecure.core.request.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class RequestContext {
    @Getter
    private RequestSpecification specification;
    private List<Config> configs;
    @Getter
    @Setter
    private long requestTime;

    public RequestContext(RequestSpecification specification) {
        this.specification = specification;
        this.configs = specification.getConfigs();
    }

    public <T extends Config> T getConfig(Class<T> configClass) {
        T config = ConfigFactory.getConfig(this.configs, configClass);

        if (config == null) {
            config = ConfigFactory.createDefaultConfig(configClass);
            this.configs.add(config);
        }

        return config;
    }

    public void setSpecification(RequestSpecification spec) {
        this.specification = spec;
        this.configs = spec.getConfigs();
    }
}
