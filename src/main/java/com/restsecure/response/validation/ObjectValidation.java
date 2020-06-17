package com.restsecure.response.validation;

import java.util.function.Predicate;

public class ObjectValidation<T> extends ResponseObjectValidation<T> {

    private final String reason;
    private final Predicate<T> predicate;

    public ObjectValidation(Class<T> responseClass, Predicate<T> predicate, String reason) {
        super(responseClass);
        this.predicate = predicate;
        this.reason = reason;
    }

    @Override
    public void validate(T responseObject) {
        if (!predicate.test(responseObject)) {
            if (reason == null) {
                throw new AssertionError("Wrong value " + responseObject);
            } else {
                throw new AssertionError(reason);
            }
        }
    }
}
