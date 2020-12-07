package com.restsecure.validation;

import com.restsecure.core.exception.RestSecureException;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationResult;

public abstract class BaseValidation implements Validation {

    @Override
    public void validate(Response response) {
        try {
            ValidationResult result = softValidate(response);

            if (result.isFail()) {
                throw new AssertionError(result.getErrorText());
            }
        } catch (Exception e) {
            throw new RestSecureException(e.getMessage());
        }
    }
}
