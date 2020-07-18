package com.restsecure.core.http;

import com.restsecure.core.util.MultiKeyMap;

import java.util.List;

public class OverrideValuesHelper {
    public static void overrideValue(String name, MultiKeyMap<String, Object> items) {
        List<Object> values = items.getAll(name);

        if (!values.isEmpty() && values.size() > 1) {
            Object value = items.getLast(name);
            items.deleteAllWithKey(name);
            items.put(name, value.toString());
        }
    }
}
