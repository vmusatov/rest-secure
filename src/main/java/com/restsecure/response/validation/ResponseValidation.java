package com.restsecure.response.validation;

import com.restsecure.response.Response;

public interface ResponseValidation {
    ValidationResult validate(Response response);
}
