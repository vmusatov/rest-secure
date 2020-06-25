package com.restsecure.http;

import com.restsecure.storage.ConfigurableStorage;

import java.util.List;

public class ParametersStorage extends ConfigurableStorage<Parameter> {

    @Override
    public void update(Parameter item) {
        super.items.add(item);
    }

    @Override
    public void update(List<Parameter> items) {
        super.items.addAll(items);
    }
}
