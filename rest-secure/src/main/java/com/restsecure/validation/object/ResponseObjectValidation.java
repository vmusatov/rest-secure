package com.restsecure.validation.object;

import com.restsecure.core.configuration.configs.ObjectMapperConfig;
import com.restsecure.core.exception.RestSecureException;
import com.restsecure.core.mapping.ObjectMapper;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.validation.BaseValidation;
import com.restsecure.validation.config.BaseJsonPathConfig;

import java.util.LinkedHashMap;
import java.util.Optional;

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

        Optional<ObjectMapper> mapper = context.getConfigValue(ObjectMapperConfig.class);

        if (mapper.isEmpty()) {
            throw new RestSecureException("ObjectMapper is null. You must configure ObjectMapper");
        }

        LinkedHashMap<String, String> value = response.getBody().get(this.path);
        String json = mapper.get().serialize(value);

        return softValidate(mapper.get().deserialize(json, responseClass));

    }
}
