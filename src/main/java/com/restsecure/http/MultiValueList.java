package com.restsecure.http;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MultiValueList<T extends NameAndValue> implements Iterable<T> {

    private List<T> items;

    public MultiValueList() {
        this.items = new ArrayList<>();
    }

    public MultiValueList(List<T> items) {
        this.items = new ArrayList<>(items);
    }

    public MultiValueList(MultiValueList<T> items) {
        this.items = new ArrayList<>(items.asList());
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void add(T item) {
        items.add(item);
    }

    public void addAll(List<T> items) {
        this.items.addAll(items);
    }

    public void addAll(MultiValueList<T> items) {
        this.items.addAll(items.asList());
    }

    public boolean contains(T item) {
        return items.contains(item);
    }

    public boolean containsAll(List<T> l) {
        return items.containsAll(l);
    }

    public T get(int index) {
        return items.get(index);
    }

    public T getFirst(String name) {
        for (T item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public MultiValueList<T> getAll(String name) {
        MultiValueList<T> result = new MultiValueList<>();
        for (T item : items) {
            if (item.getName().equals(name)) {
                result.add(item);
            }
        }
        return result;
    }

    public String getValue(String name) {
        T item = getFirst(name);
        if (item == null) {
            return null;
        }
        return item.getValue();
    }

    public List<String> getAllValues(String name) {
        List<String> result = new LinkedList<>();
        List<T> findItems = getAll(name).asList();

        for (T item : findItems) {
            result.add(item.getValue());
        }
        return result;
    }

    public List<T> asList() {
        return items;
    }

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }

    @Override
    public String toString() {
        return items.toString();
    }
}
