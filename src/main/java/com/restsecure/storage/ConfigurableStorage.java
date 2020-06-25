package com.restsecure.storage;

import com.restsecure.configuration.Config;
import com.restsecure.configuration.ConfigsStorage;

import java.util.List;

public abstract class ConfigurableStorage<T> extends BaseStorage<T> {

    protected final ConfigsStorage configs;

    public ConfigurableStorage() {
        super();
        this.configs = new ConfigsStorage();
    }

    public ConfigurableStorage(List<T> items) {
        super(items);
        this.configs = new ConfigsStorage();
    }

    public void addConfig(Config config) {
        this.configs.update(config);
    }
}
