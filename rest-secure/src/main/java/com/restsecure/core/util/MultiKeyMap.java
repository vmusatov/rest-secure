package com.restsecure.core.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MultiKeyMap<T, E> implements Iterable<MultiKeyMap.Entity<T, E>> {
    private final ArrayList<Entity<T, E>> items;

    public MultiKeyMap() {
        this.items = new ArrayList<>();
    }

    public void put(T key, E value) {
        this.items.add(new Entity<>(key, value));
    }

    public E getFirst(T key) {
        for (Entity<T, E> item : items) {
            if (item.getKey().equals(key)) {
                return item.getValue();
            }
        }
        return null;
    }

    public E getLast(T key) {
        for (int i = this.items.size() - 1; i > 0; i--) {
            Entity<T, E> item = items.get(i);
            if (item.getKey().equals(key)) {
                return item.getValue();
            }
        }
        return null;
    }

    public List<E> getAll(T key) {
        List<E> result = new ArrayList<>();
        for (Entity<T, E> item : items) {
            if (item.getKey().equals(key)) {
                result.add(item.getValue());
            }
        }
        return result;
    }

    public boolean containsKey(T key) {
        for (Entity<T, E> item : items) {
            if (item.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public void deleteAllWithKey(T key) {
        this.items.removeIf(item -> item.getKey().equals(key));
    }

    public void clear() {
        this.items.clear();
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public int size() {
        return this.items.size();
    }

    @Override
    public Iterator<MultiKeyMap.Entity<T, E>> iterator() {
        return items.iterator();
    }

    @Data
    @AllArgsConstructor
    public static class Entity<T, E> {
        private T key;
        private E value;
    }
}
