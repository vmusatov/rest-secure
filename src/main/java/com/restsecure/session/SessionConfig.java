package com.restsecure.session;

import com.restsecure.configuration.Config;
import lombok.Getter;

/**
 * SessionConfig will help tell the RestSecure what name the session cookie has<br>
 * By default RestSecure use {@value SessionConfig#DEFAULT_SESSION_ID_NAME} name
 */
public class SessionConfig implements Config {

    public static final String DEFAULT_SESSION_ID_NAME = "JSESSIONID";

    @Getter
    private String sessionIdName;

    public SessionConfig setSessionIdName(String sessionIdName) {
        this.sessionIdName = sessionIdName;
        return this;
    }

    @Override
    public void reset() {
        this.sessionIdName = DEFAULT_SESSION_ID_NAME;
    }
}
