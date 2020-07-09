package com.restsecure.core.processor;

import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.request.RequestContext;

public interface PostResponseValidationProcessor {
    ValidationResult validate(RequestContext context);
}
