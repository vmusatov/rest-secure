package com.restsecure.validation.object;

import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.response.Response;
import com.restsecure.core.processor.PostResponseValidationProcessor;

public abstract class ResponseObjectValidation<T> implements PostResponseValidationProcessor {

    protected final Class<T> responseClass;

    public ResponseObjectValidation(Class<T> responseClass) {
        this.responseClass = responseClass;
    }

    protected abstract ValidationResult validate(T responseObject);

    @Override
    public ValidationResult validate(RequestContext context) {
        Response response = context.getResponse();

        return validate(response.getBody().as(responseClass));
    }
}
