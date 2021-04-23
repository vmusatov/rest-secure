package com.restsecure.core.configuration.configs;

import com.restsecure.core.configuration.BaseConfig;

import java.util.Optional;

public class BaseUrlConfig extends BaseConfig<Optional<String>> {
    @Override
    public void initDefault() {
        value = Optional.empty();
    }
}
