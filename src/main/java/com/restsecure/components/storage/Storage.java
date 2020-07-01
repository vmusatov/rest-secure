package com.restsecure.components.storage;

import java.util.List;

public interface Storage<T> {
    void update(T item);

    void update(List<T> items);

    List<T> getAll();
}
