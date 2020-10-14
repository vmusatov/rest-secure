package com.restsecure.core.util;

import com.restsecure.core.exception.RestSecureException;
import com.restsecure.core.http.NameAndValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NameValueList<T extends NameAndValue> implements Iterable<T> {

    private List<T> items;

    public NameValueList() {
        this.items = new ArrayList<>();
    }

    public NameValueList(List<T> items) {
        this.items = new ArrayList<>(items);
    }

    public NameValueList(NameValueList<T> items) {
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

    public void addAll(NameValueList<T> items) {
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
            if (itemHasName(item, name)) {
                return item;
            }
        }
        return null;
    }

    public T getLast(String name) {
        for (int i = items.size() - 1; i > 0; i--) {
            T item = items.get(i);
            if (itemHasName(item, name)) {
                return item;
            }
        }
        return null;
    }

    public NameValueList<T> getAll(String name) {
        NameValueList<T> result = new NameValueList<>();
        for (T item : items) {
            if (itemHasName(item, name)) {
                result.add(item);
            }
        }
        return result;
    }

    public String getFirstValue(String name) {
        T item = getFirst(name);
        if (item == null) {
            throw new RestSecureException("Not found item with name " + name);
        }
        return item.getValue();
    }

    public <E> E getFirstValue(String name, Function<String, E> parsingFunction) {
        String value = getFirstValue(name);
        return parsingFunction.apply(value);
    }

    public String getLastValue(String name) {
        T item = getLast(name);
        if (item == null) {
            throw new RestSecureException("Not found item with name " + name);
        }
        return item.getValue();
    }

    public <E> E getLastValue(String name, Function<String, E> parsingFunction) {
        String value = getLastValue(name);
        return parsingFunction.apply(value);
    }

    public List<String> getAllValues(String name) {
        List<String> result = new ArrayList<>();
        List<T> findItems = getAll(name).asList();

        for (T item : findItems) {
            result.add(item.getValue());
        }

        return result;
    }

    public <E> List<E> getAllValues(String name, Function<String, E> parsingFunction) {
        List<String> values = getAllValues(name);

        return values.stream()
                .map(parsingFunction)
                .collect(Collectors.toList());
    }

    public List<T> asList() {
        return items;
    }

    private boolean itemHasName(T item, String name) {
        return item != null && item.getName().equals(name);
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
