package com.restsecure.validation.object;

import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.response.validation.ValidationStatus;

import java.util.function.Predicate;

import static com.restsecure.core.response.validation.ValidationStatus.SUCCESS;

public class ObjectValidation<T> extends ResponseObjectValidation<T> {

    private final String reason;
    private final Predicate<T> predicate;

    public ObjectValidation(String path, Class<T> responseClass, Predicate<T> predicate, String reason) {
        super(path, responseClass);
        this.predicate = predicate;
        this.reason = reason;
    }

    @Override
    public ValidationResult validate(T responseObject) {
        if (!predicate.test(responseObject)) {
            if (reason == null) {
                return new ValidationResult(ValidationStatus.FAIL);
            } else {
                return new ValidationResult(ValidationStatus.FAIL, reason);
            }
        }

        return new ValidationResult(SUCCESS);
    }
}
