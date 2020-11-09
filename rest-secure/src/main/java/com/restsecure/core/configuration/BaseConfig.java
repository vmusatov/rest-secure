package com.restsecure.core.configuration;

public abstract class BaseConfig<T> implements Config<T> {
    protected T value;

    @Override
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return this.value;
    }

    abstract public void initDefault();
}
