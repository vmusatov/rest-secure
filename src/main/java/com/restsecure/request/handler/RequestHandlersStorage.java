package com.restsecure.request.handler;

import com.restsecure.components.authentication.RequestAuthHandler;
import com.restsecure.components.storage.ConfigurableStorage;

import java.util.List;

public class RequestHandlersStorage extends ConfigurableStorage<RequestHandler> {

    @Override
    public void update(RequestHandler item) {
        if (item instanceof RequestAuthHandler) {
            super.items.removeIf(handler -> handler instanceof RequestAuthHandler);
        }
        super.items.add(item);
    }

    @Override
    public void update(List<RequestHandler> items) {
        for (RequestHandler requestHandler : items) {
            update(requestHandler);
        }
    }
}
