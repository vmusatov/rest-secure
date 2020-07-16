package com.restsecure.core.http;

import com.restsecure.core.util.MultiKeyMap;

import java.util.List;

public class OverrideValuesHelper {
    public static void overrideValue(String name, MultiKeyMap<String, Object> params) {
        List<Object> values = params.getAll(name);

        if (!values.isEmpty() && values.size() > 1) {
            Object value = params.getLast(name);
            params.deleteAllWithKey(name);
            params.put(name, value.toString());
        }
    }
}
