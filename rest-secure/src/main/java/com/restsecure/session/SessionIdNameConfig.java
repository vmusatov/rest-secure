package com.restsecure.session;

import com.restsecure.core.configuration.BaseConfig;

public class SessionIdNameConfig extends BaseConfig<String> {

    public static final String DEFAULT_SESSION_ID_NAME = "JSESSIONID";

    @Override
    public void initDefault() {
        value = DEFAULT_SESSION_ID_NAME;
    }
}
