package com.restsecure.data;

import com.restsecure.core.exception.RestSecureException;

import java.lang.reflect.Field;

public class ReflectUtil {

    public static Object getFieldValue(Object obj, Field field) {
        field.setAccessible(true);

        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw new RestSecureException(e);
        }
    }
}
