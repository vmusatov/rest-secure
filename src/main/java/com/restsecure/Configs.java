package com.restsecure;

import com.restsecure.configuration.ConfigFactory;
import com.restsecure.deserialize.DeserializeConfig;
import com.restsecure.session.SessionConfig;

public class Configs {

    /**
     * Create a new SessionConfig instance with default settings
     *
     * @return SessionConfig
     */
    public static SessionConfig sessionConfig() {
        return ConfigFactory.createDefaultConfig(SessionConfig.class);
    }

    /**
     * Create a new DeserializeConfig instance with default settings
     *
     * @return DeserializeConfig
     */
    public static DeserializeConfig deserializeConfig() {
        return ConfigFactory.createDefaultConfig(DeserializeConfig.class);
    }
}
