package com.restsecure.response.validation;

import com.restsecure.components.storage.BaseStorage;

import java.util.List;

public class ResponseValidationsStorage extends BaseStorage<ResponseValidation> {

    @Override
    public void update(ResponseValidation item) {

        if (hasDefaultValidation()) {
            removeDefaultValidations();
        }

        if (isDefaultValidation(item)) {
            if (super.items.isEmpty()) {
                super.items.add(item);
            }
        } else {
            super.items.add(item);
        }
    }

    @Override
    public void update(List<ResponseValidation> items) {
        for (ResponseValidation validation : items) {
            update(validation);
        }
    }

    private boolean isDefaultValidation(ResponseValidation validation) {
        return validation instanceof DefaultValidation;
    }

    private boolean hasDefaultValidation() {
        for (ResponseValidation validation : super.items) {
            if (isDefaultValidation(validation)) {
                return true;
            }
        }

        return false;
    }

    private void removeDefaultValidations() {
        super.items.removeIf(this::isDefaultValidation);
    }
}
