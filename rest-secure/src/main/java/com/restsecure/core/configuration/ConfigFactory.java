package com.restsecure.core.configuration;

import com.restsecure.core.exception.RestSecureException;

import java.util.List;

public class ConfigFactory {

    /**
     * Create new config instance with default settings
     *
     * @param configClass config class
     * @return specified config
     */
    public static <T extends Config<?>> T createDefaultConfig(Class<T> configClass) {
        try {
            T config = configClass.getDeclaredConstructor().newInstance();
            config.initDefault();

            return config;
        }
        catch (NoSuchMethodException e) {
            throw new RestSecureException("No default constructor found. To create a config, the " + configClass + " class must have a default constructor.");
        }
        catch (Exception e) {
            throw new RestSecureException(e);
        }
    }

    /**
     * Get specified config from configs list
     *
     * @param configs     configs list
     * @param configClass specified config
     * @return specified config
     */
    public static <T extends Config<?>> T getConfig(List<Config<?>> configs, Class<T> configClass) {
        for (Config<?> config : configs) {
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
    public static <T extends Config<?>> T getConfigOrCreateDefault(List<Config<?>> configs, Class<T> configClass) {
        T config = getConfig(configs, configClass);

        if (config == null) {
            return createDefaultConfig(configClass);
        }

        return config;
    }
}
