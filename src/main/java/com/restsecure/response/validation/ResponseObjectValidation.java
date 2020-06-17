package com.restsecure.response.validation;

import com.restsecure.response.Response;

public abstract class ResponseObjectValidation<T> implements ResponseValidation {

    protected final Class<T> responseClass;

    public ResponseObjectValidation(Class<T> responseClass) {
        this.responseClass = responseClass;
    }

    protected abstract void validate(T responseObject);

    @Override
    public void validate(Response response) {
        validate(response.getBody().as(responseClass));
    }
}
