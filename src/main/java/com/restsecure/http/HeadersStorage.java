package com.restsecure.http;

import com.restsecure.components.storage.ConfigurableStorage;

import java.util.List;

public class HeadersStorage extends ConfigurableStorage<Header> {

    @Override
    public void update(Header item) {
        super.items.add(item);
    }

    @Override
    public void update(List<Header> items) {
        super.items.addAll(items);
    }
}
