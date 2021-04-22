package com.restsecure.core.configuration.configs;

import com.restsecure.core.configuration.BaseConfig;

public class SocketTimeoutConfig extends BaseConfig<Integer> {

    public static final int DEFAULT_SOCKET_TIMEOUT = 60000;

    @Override
    public void initDefault() {
        value = DEFAULT_SOCKET_TIMEOUT;
    }
}
