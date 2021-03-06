package com.restsecure.core.request;

import com.restsecure.RestSecure;
import com.restsecure.core.configuration.Config;
import com.restsecure.core.configuration.ConfigFactory;
import com.restsecure.core.request.specification.RequestSpec;
import com.restsecure.core.request.specification.RequestSpecImpl;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class RequestContext {
    @Getter
    private RequestSpec requestSpec;
    private List<Config<?>> configs;
    @Getter
    @Setter
    private long requestTime;

    public RequestContext() {
        init(null);
    }

    public RequestContext(RequestSpec spec) {
        init(spec);
    }

    private void init(RequestSpec spec) {
        this.requestSpec = new RequestSpecImpl();
        this.requestSpec.mergeWith(RestSecure.getGlobalRequestSpec());

        if (spec != null) {
            this.requestSpec.mergeWith(spec);
        }

        this.configs = this.requestSpec.getConfigs();
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
