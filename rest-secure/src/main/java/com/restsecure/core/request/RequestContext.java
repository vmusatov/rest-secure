package com.restsecure.core.request;

import com.restsecure.RestSecure;
import com.restsecure.core.configuration.Config;
import com.restsecure.core.configuration.ConfigFactory;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.request.specification.RequestSpecificationImpl;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class RequestContext {
    @Getter
    private RequestSpecification specification;
    private List<Config<?>> configs;
    @Getter
    @Setter
    private long requestTime;

    public RequestContext() {
        init(null);
    }

    public RequestContext(RequestSpecification specification) {
        init(specification);
    }

    private void init(RequestSpecification specification) {
        this.specification = new RequestSpecificationImpl();

        if (specification != null) {
            this.specification.mergeWith(specification);
        }
        this.specification.mergeWith(RestSecure.globalSpecification);

        this.configs = this.specification.getConfigs();
    }

    public <T, E extends Config<T>> T getConfigValue(Class<E> configClass) {
        Config<T> config = getConfig(configClass);
        return config.getValue();
    }

    public <T extends Config<?>> T getConfig(Class<T> configClass) {
        T config = ConfigFactory.getConfig(this.configs, configClass);

        if (config == null) {
            config = ConfigFactory.createDefaultConfig(configClass);
            this.configs.add(config);
        }

        return config;
    }
}
