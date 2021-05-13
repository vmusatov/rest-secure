package com.restsecure.core.response.validation;

import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.Response;

public interface Validation {
    ValidationResult softValidate(RequestContext context, Response response);

    default void validate(RequestContext context, Response response) {
        ValidationResult result = softValidate(context, response);

        if (result.isFail()) {
            throw new AssertionError(result.getErrorText());
        }
    }
}
