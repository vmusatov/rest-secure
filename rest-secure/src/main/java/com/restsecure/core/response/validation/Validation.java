package com.restsecure.core.response.validation;

import com.restsecure.core.response.Response;

public interface Validation {
    ValidationResult softValidate(Response response);

    default void validate(Response response) {
        ValidationResult result = softValidate(response);

        if (result.isFail()) {
            throw new AssertionError(result.getErrorText());
        }
    }
}
