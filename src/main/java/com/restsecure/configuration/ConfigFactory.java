package com.restsecure.configuration;

import java.util.List;

public class ConfigFactory {

    /**
     * Create new config instance with default settings
     *
     * @param configClass config class
     * @return specified config
     */
    public static <T extends Config> T createDefaultConfig(Class<T> configClass) {
        try {
            T config = configClass.getDeclaredConstructor().newInstance();
            config.reset();

            return config;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get specified config from configs list
     *
     * @param configs     configs list
     * @param configClass specified config
     * @return specified config
     */
    public static <T extends Config> T getConfig(List<Config> configs, Class<T> configClass) {
        for (Config config : configs) {
            if (configClass.isInstance(config)) {
                return (T) config;
            }
        }

        return null;
    }

    /**
     * Get specified config from configs list<br>
     * If the config list does not contain the specified config, a new config instance will be returned with the default settings
     *  
     *
     * @param configs     configs list
     * @param configClass specified config
     * @return specified config
     */
    public static <T extends Config> T getConfigOrCreateDefault(List<Config> configs, Class<T> configClass) {
        T config = getConfig(configs, configClass);

        if (config == null) {
            return createDefaultConfig(configClass);
        }

        return config;
    }
}
