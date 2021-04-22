package com.restsecure.core.configuration.configs;

import com.restsecure.core.configuration.BaseConfig;
import com.restsecure.core.http.Host;

import java.util.Optional;

public class ProxyConfig extends BaseConfig<Optional<Host>> {
    @Override
    public void initDefault() {
        value = Optional.empty();
    }
}
