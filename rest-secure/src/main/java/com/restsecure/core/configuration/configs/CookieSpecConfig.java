package com.restsecure.core.configuration.configs;

import com.restsecure.core.configuration.BaseConfig;

public class CookieSpecConfig extends BaseConfig<String> {

    public static final String DEFAULT_COOKIE_SPEC = "default";

    @Override
    public void initDefault() {
        value = DEFAULT_COOKIE_SPEC;
    }
}
