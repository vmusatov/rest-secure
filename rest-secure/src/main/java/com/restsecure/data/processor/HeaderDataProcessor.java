package com.restsecure.data.processor;

import com.restsecure.core.http.header.Header;
import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.data.annotation.RequestHeader;

import java.lang.reflect.Field;
import java.util.List;

import static com.restsecure.data.ReflectUtil.getFieldValue;

@ProcessAll
public class HeaderDataProcessor implements Processor {

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
                if (field.isAnnotationPresent(RequestHeader.class)) {
                    setHeaderFromField(field, dataClass, spec);
                }
            }
        }
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

        Object value = getFieldValue(dataClass, field);

        if (value instanceof Header) {
            Header header = (Header) value;
            header.setName(name);
            spec.header(header);
        } else {
            spec.header(name, value);
        }
    }


    @Override
    public int getRequestProcessOrder() {
        return Processor.MIN_ORDER;
    }
}
