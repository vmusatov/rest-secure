package com.restsecure.data.processor;

import com.restsecure.core.http.param.Parameter;
import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.data.annotation.RequestParam;

import java.lang.reflect.Field;
import java.util.List;

import static com.restsecure.data.ReflectUtil.getFieldValue;

@ProcessAll
public class ParamsDataProcessor implements Processor {

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
                if (field.isAnnotationPresent(RequestParam.class)) {
                    setParamFromField(field, dataClass, spec);
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

        Object value = getFieldValue(dataClass, field);

        if (value instanceof Parameter) {
            Parameter param = (Parameter) value;
            param.setName(name);
            spec.param(param);
        } else {
            spec.param(name, value);
        }
    }

    @Override
    public int getRequestProcessOrder() {
        return Processor.MIN_ORDER;
    }
}
