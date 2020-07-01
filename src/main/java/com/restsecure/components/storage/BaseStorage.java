package com.restsecure.components.storage;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseStorage<T> implements Storage<T> {

    protected final List<T> items;

    public BaseStorage() {
        this.items = new ArrayList<>();
    }

    public BaseStorage(List<T> items) {
        this.items = items;
    }

    @Override
    public List<T> getAll() {
        return this.items;
    }
}