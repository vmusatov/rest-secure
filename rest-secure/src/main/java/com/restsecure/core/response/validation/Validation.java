package com.restsecure.core.response.validation;

import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.request.RequestContext;

public interface Validation {
    ValidationResult validate(RequestContext context, Response response);
}
