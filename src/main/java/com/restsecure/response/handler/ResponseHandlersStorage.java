package com.restsecure.response.handler;

import com.restsecure.components.storage.ConfigurableStorage;

import java.util.List;

public class ResponseHandlersStorage extends ConfigurableStorage<ResponseHandler> {

    @Override
    public void update(ResponseHandler item) {
        super.items.add(item);
    }

    @Override
    public void update(List<ResponseHandler> items) {
        super.items.addAll(items);
    }
}
