package com.restsecure;

import com.restsecure.core.apache.ApacheConfig;
import com.restsecure.core.configuration.ConfigFactory;
import com.restsecure.core.http.header.HeadersConfig;
import com.restsecure.core.mapping.deserialize.DeserializeConfig;
import com.restsecure.core.mapping.serialize.SerializeConfig;
import com.restsecure.logging.LogConfig;
import com.restsecure.session.SessionConfig;

public class Configs {

    public static ApacheConfig apacheConfig() {
        return ConfigFactory.createDefaultConfig(ApacheConfig.class);
    }

    /**
     * Create a new HeadersConfig instance with default settings
     *
     * @return HeadersConfig
     */
    public static HeadersConfig headersConfig() {
        return ConfigFactory.createDefaultConfig(HeadersConfig.class);
    }

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

    /**
     * Create a new SerializeConfig instance with default settings
     *
     * @return SerializeConfig
     */
    public static SerializeConfig serializeConfig() {
        return ConfigFactory.createDefaultConfig(SerializeConfig.class);
    }

    /**
     * Create a new LogConfig instance with default settings
     * @return LogConfig
     */
    public static LogConfig logConfig() {
        return ConfigFactory.createDefaultConfig(LogConfig.class);
    }
}
