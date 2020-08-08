package com.restsecure.data.processor;

import com.restsecure.core.http.cookie.Cookie;
import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.data.annotation.RequestCookie;

import java.lang.reflect.Field;
import java.util.List;

import static com.restsecure.data.ReflectUtil.getFieldValue;

@ProcessAll
public class CookieDataProcessor implements Processor {

    @Override
    public void processRequest(RequestContext context) {
        RequestSpecification spec = context.getSpecification();
        List<Object> data = spec.getData();

        if (data == null || data.isEmpty()) {
            return;
        }

        for (Object dataClass : data) {
            if (dataClass == null) {
                continue;
            }

            Field[] fields = dataClass.getClass().getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(RequestCookie.class)) {
                    setCookieFromField(field, dataClass, spec);
                }
            }
        }
    }

    private static void setCookieFromField(Field field, Object dataClass, RequestSpecification spec) {
        String name;
        RequestCookie cookieAnnotation = field.getAnnotation(RequestCookie.class);

        if (!cookieAnnotation.enable()) {
            return;
        }

        if (cookieAnnotation.name().isEmpty()) {
            name = field.getName();
        } else {
            name = cookieAnnotation.name();
        }

        Object value = getFieldValue(dataClass, field);

        if (value instanceof Cookie) {
            Cookie cookie = (Cookie) value;
            cookie.setName(name);
            spec.cookie(cookie);
        } else {
            spec.cookie(name, value);
        }
    }

    @Override
    public int getRequestProcessOrder() {
        return Processor.MIN_ORDER;
    }
}
