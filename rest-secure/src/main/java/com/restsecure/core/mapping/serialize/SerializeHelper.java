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

    public static void serializeValuesIfNeed(MultiKeyMap<String, Object> items, Serializer serializer) {
        items.forEach(item -> {
            Object value = item.getValue();
            if (isNeedSerialize(value)) {
                item.setValue(serializer.serialize(value));
            }
        });
    }
}
