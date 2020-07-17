package com.restsecure.validation.object;

import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;

public abstract class ResponseObjectValidation<T> implements Validation {

    protected final Class<T> responseClass;

    public ResponseObjectValidation(Class<T> responseClass) {
        this.responseClass = responseClass;
    }

    protected abstract ValidationResult validate(RequestContext context, T responseObject);

    @Override
    public ValidationResult validate(RequestContext context, Response response) {

        return validate(context, response.getBody().as(responseClass));
    }
}
