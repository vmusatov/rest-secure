package com.restsecure.core.response.validation;

import com.restsecure.core.response.Response;

public interface Validation {
    void validate(Response response);

    ValidationResult softValidate(Response response);
}
