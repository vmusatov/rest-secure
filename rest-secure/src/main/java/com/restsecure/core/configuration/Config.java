package com.restsecure.core.configuration;

/**
 * Interface for RestSecure configs
 */
public interface Config<T> {
    void setValue(T value);

    T getValue();

    void initDefault();
}
