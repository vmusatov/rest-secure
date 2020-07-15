package com.restsecure.core.mapping.serialize;

import com.restsecure.core.util.MultiKeyMap;

public class SerializeHelper {

    public static boolean isNeedSerialize(Object object) {

        if (object == null) {
            return false;
        }

        Class<?> objectClass = object.getClass();
        return !(Number.class.isAssignableFrom(objectClass) || Boolean.class.isAssignableFrom(objectClass)
                || String.class.isAssignableFrom(objectClass) || Character.class.isAssignableFrom(objectClass)
                || objectClass.isEnum() || Class.class.isAssignableFrom(objectClass));
    }

    public static void serializeValuesIfNeed(MultiKeyMap<String, Object> headers, SerializeConfig serializeConfig) {
        headers.forEach(header -> {
            Object value = header.getValue();
            if (isNeedSerialize(value)) {
                header.setValue(serializeConfig.getSerializer().serialize(value));
            }
        });
    }
}
