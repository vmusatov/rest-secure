package com.restsecure.data;

import com.restsecure.request.RequestHandler;
import com.restsecure.request.specification.RequestSpecification;

import java.lang.reflect.Field;

/**
 * This request handler extracts data from the data class and adds it to the request specification<br>
 */
public class RequestDataHandler implements RequestHandler {

    @Override
    public void handleRequest(RequestSpecification spec) {
        Object dataClass = spec.getData();

        if (dataClass != null) {
            Field[] fields = dataClass.getClass().getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(RequestParam.class)) {
                    setParamFromField(field, dataClass, spec);
                }
                if (field.isAnnotationPresent(RequestHeader.class)) {
                    setHeaderFromField(field, dataClass, spec);
                }
            }
        }
    }

    private void setParamFromField(Field field, Object dataClass, RequestSpecification spec) {
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

    private void setHeaderFromField(Field field, Object dataClass, RequestSpecification spec) {
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

    private String getFieldValue(Object obj, Field field) {
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
