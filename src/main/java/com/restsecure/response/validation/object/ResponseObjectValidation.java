package com.restsecure.response.validation.object;

import com.restsecure.response.Response;
import com.restsecure.response.validation.ResponseValidation;
import com.restsecure.response.validation.ValidationResult;

public abstract class ResponseObjectValidation<T> implements ResponseValidation {

    protected final Class<T> responseClass;

    public ResponseObjectValidation(Class<T> responseClass) {
        this.responseClass = responseClass;
    }

    protected abstract ValidationResult validate(T responseObject);

    @Override
    public final ValidationResult validate(Response response) {
        return validate(response.getBody().as(responseClass));
    }
}
