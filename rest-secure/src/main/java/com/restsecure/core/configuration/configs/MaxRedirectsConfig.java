package com.restsecure.core.configuration.configs;

import com.restsecure.core.configuration.BaseConfig;

public class MaxRedirectsConfig extends BaseConfig<Integer> {

    public static final int DEFAULT_MAX_REDIRECTS = 50;

    @Override
    public void initDefault() {
        value = DEFAULT_MAX_REDIRECTS;
    }
}
