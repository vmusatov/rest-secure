package com.restsecure.core.http;

import com.restsecure.core.util.MultiKeyMap;

import java.util.ArrayList;
import java.util.List;

public class OverwriteValuesHelper {
    public static void overwriteValue(String key, MultiKeyMap<String, Object> items) {
        List<Object> values = items.getAll(key);

        if (!values.isEmpty() && values.size() > 1) {
            Object value = items.getLast(key);
            items.deleteAllWithKey(key);
            items.put(key, String.valueOf(value));
        }
    }

    public static void overwriteValues(MultiKeyMap<String, Object> items, List<String> keysToOverwrite) {
        for (String name : keysToOverwrite) {
            overwriteValue(name, items);
        }
    }

    public static void overwriteAllValues(MultiKeyMap<String, Object> items) {
        List<String> names = new ArrayList<>();
        items.forEach(p -> names.add(p.getKey()));
        overwriteValues(items, names);
    }
}
