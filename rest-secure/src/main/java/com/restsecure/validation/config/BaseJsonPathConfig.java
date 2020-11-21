package com.restsecure.validation.config;

import com.restsecure.core.configuration.BaseConfig;

public class BaseJsonPathConfig extends BaseConfig<String> {
    @Override
    public void initDefault() {
        value = "";
    }
}
