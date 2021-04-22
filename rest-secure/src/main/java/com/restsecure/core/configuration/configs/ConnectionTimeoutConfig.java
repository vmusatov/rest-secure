package com.restsecure.core.configuration.configs;

import com.restsecure.core.configuration.BaseConfig;

public class ConnectionTimeoutConfig extends BaseConfig<Integer> {

    public static final int DEFAULT_CONNECTION_TIMEOUT = 10000;

    @Override
    public void initDefault() {
        value = DEFAULT_CONNECTION_TIMEOUT;
    }
}
