package com.restsecure.core.configuration.configs;

import com.restsecure.core.configuration.BaseConfig;

public class EnableRedirectsConfig extends BaseConfig<Boolean> {
    @Override
    public void initDefault() {
        value = true;
    }
}
