package com.restsecure.core.request.data;

import com.restsecure.core.request.RequestContext;
import com.restsecure.core.processor.PreSendProcessor;
import com.restsecure.core.request.specification.RequestSpecification;

import java.lang.reflect.Field;

public class RequestDataConfigurator {

    public static void configure(RequestContext context) {
        Object dataClass = context.getSpecification().getData();

        if (dataClass != null) {
            Field[] fields = dataClass.getClass().getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(RequestParam.class)) {
                    setParamFromField(field, dataClass, context.getSpecification());
                }
                if (field.isAnnotationPresent(RequestHeader.class)) {
                    setHeaderFromField(field, dataClass, context.getSpecification());
                }
            }
        }
    }

    private static void setParamFromField(Field field, Object dataClass, RequestSpecification spec) {
        String name;
        RequestParam paramAnnotation = field.getAnnotation(RequestParam.class);

        if (!paramAnnotation.enable()) {
            return;
        }

        if (paramAnnotation.name().isEmpty()) {
            name = field.getName();
        } else {
            name = paramAnnotation.name();
        }

        String value = getFieldValue(dataClass, field);
        spec.param(name, value);
    }

    private static void setHeaderFromField(Field field, Object dataClass, RequestSpecification spec) {
        String name;
        RequestHeader headerAnnotation = field.getAnnotation(RequestHeader.class);

        if (!headerAnnotation.enable()) {
            return;
        }

        if (headerAnnotation.name().isEmpty()) {
            name = field.getName();
        } else {
            name = headerAnnotation.name();
        }

        String value = getFieldValue(dataClass, field);
        spec.header(name, value);
    }

    private static String getFieldValue(Object obj, Field field) {
        boolean canAccess = field.canAccess(obj);
        field.setAccessible(true);

        try {
            Object value = field.get(obj);
            if (value == null) {
                return null;
            }
            return value.toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if (!canAccess) {
                field.setAccessible(false);
            }
        }
    }
}
