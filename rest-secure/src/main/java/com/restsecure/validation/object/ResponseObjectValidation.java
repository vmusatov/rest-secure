package com.restsecure.validation.object;

import com.restsecure.core.configuration.configs.DeserializerConfig;
import com.restsecure.core.configuration.configs.SerializerConfig;
import com.restsecure.core.mapping.deserialize.Deserializer;
import com.restsecure.core.mapping.serialize.Serializer;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.validation.BaseValidation;
import com.restsecure.validation.config.BaseJsonPathConfig;

import java.util.LinkedHashMap;

public abstract class ResponseObjectValidation<T> extends BaseValidation {

    protected final Class<T> responseClass;
    private String path;

    public ResponseObjectValidation(String path, Class<T> responseClass) {
        this.responseClass = responseClass;
        this.path = path;
    }

    protected abstract ValidationResult softValidate(T responseObject);

    @Override
    public ValidationResult softValidate(Response response) {
        RequestContext context = response.getContext();
        String basePath = context.getConfigValue(BaseJsonPathConfig.class);

        if (basePath != null && !basePath.isEmpty()) {
            this.path = basePath + path;
        }

        if (this.path.isEmpty()) {
            return softValidate(response.getBody().as(responseClass));
        }

        Serializer serializer = context.getConfigValue(SerializerConfig.class);
        Deserializer deserializer = context.getConfigValue(DeserializerConfig.class);

        LinkedHashMap<String, String> value = response.getBody().get(this.path);
        String json = serializer.serialize(value);

        return softValidate(deserializer.deserialize(json, responseClass));

    }
}
