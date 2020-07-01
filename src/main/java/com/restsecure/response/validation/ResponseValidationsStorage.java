package com.restsecure.response.validation;

import com.restsecure.components.storage.ConfigurableStorage;

import java.util.List;

public class ResponseValidationsStorage extends ConfigurableStorage<ResponseValidation> {

    @Override
    public void update(ResponseValidation item) {
        super.items.add(item);
    }

    @Override
    public void update(List<ResponseValidation> items) {
        super.items.addAll(items);
    }
}
