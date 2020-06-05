package com.restsecure;

import com.restsecure.configuration.ConfigFactory;
import com.restsecure.configuration.DeserializeConfig;
import com.restsecure.configuration.SessionConfig;
import com.restsecure.deserialize.Deserializer;

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
     * Create a new SessionConfig instance with specified sessionIdName<br>
     * Shortcut for
     * <pre>
     *     sessionConfig().setSessionIdName("PHPSESSID");
     * </pre>
     *
     * @param name session id name
     * @return SessionConfig
     */
    public static SessionConfig sessionIdName(String name) {
        return sessionConfig().setSessionIdName(name);
    }

    /**
     * Create a new DeserializeConfig instance with default settings
     *
     * @return DeserializeConfig
     */
    public static DeserializeConfig deserializeConfig() {
        return ConfigFactory.createDefaultConfig(DeserializeConfig.class);
    }

    /**
     * Create a new DeserializeConfig instance with specified deserializer<br>
     * Shortcut for
     * <pre>
     *     deserializeConfig().setDeserializer(deserializer);
     * </pre>
     *
     * @param deserializer deserializer
     * @return DeserializeConfig
     */
    public static DeserializeConfig deserializer(Deserializer deserializer) {
        return deserializeConfig().setDeserializer(deserializer);
    }
}
