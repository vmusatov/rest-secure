package com.restsecure.core.configuration.configs;

import com.restsecure.core.configuration.BaseConfig;
import com.restsecure.core.mapping.ObjectMapper;

import java.util.Optional;

public class ObjectMapperConfig extends BaseConfig<Optional<ObjectMapper>> {
    @Override
    public void initDefault() {
        value = Optional.empty();
    }
}
