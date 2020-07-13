package com.restsecure.core.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MultiValueMap<T, E> implements Iterable<MultiValueMap.Entity<T, E>> {
    private ArrayList<Entity<T, E>> items;

    public MultiValueMap() {
        this.items = new ArrayList<>();
    }

    public void put(T key, E value) {
        boolean isAdded = false;
        for(Entity<T, E> entity : items) {
            if(entity.key.equals(key)) {
                entity.value.add(value);
                isAdded = true;
            }
        }

        if(!isAdded) {
            ArrayList<E> valuesList = new ArrayList<>();
            valuesList.add(value);
            items.add(new Entity<>(key, valuesList));
        }
    }

    public List<E> get(T key) {
        for(Entity<T, E> entity : items) {
            if(entity.key.equals(key)) {
                return entity.value;
            }
        }

        return Collections.emptyList();
    }

    @Override
    public Iterator<Entity<T, E>> iterator() {
        return items.iterator();
    }

    @Data
    @AllArgsConstructor
    public static class Entity<T, E> {
        private T key;
        private ArrayList<E> value;
    }
}
