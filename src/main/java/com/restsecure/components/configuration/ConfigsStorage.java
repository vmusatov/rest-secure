package com.restsecure.components.configuration;

import com.restsecure.components.storage.BaseStorage;

import java.util.List;

public class ConfigsStorage extends BaseStorage<Config> {

    @Override
    public void update(Config item) {
        if (hasConfig(item.getClass())) {
            super.items.removeIf(item.getClass()::isInstance);
        }

        super.items.add(item);
    }

    public boolean hasConfig(Class<? extends Config> configClass) {
        return ConfigFactory.getConfig(super.items, configClass) != null;
    }

    @Override
    public void update(List<Config> items) {
        for (Config config : items) {
            update(config);
        }
    }

    public <E extends Config> E get(Class<E> itemClass) {
        return ConfigFactory.getConfig(super.items, itemClass);
    }
}
