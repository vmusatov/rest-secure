package com.restsecure.validation.composite;

import com.restsecure.core.processor.PostResponseValidationProcessor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.validation.ValidationResult;

import java.util.List;

public class BaseCompositeValidation extends CompositeValidation {

    public BaseCompositeValidation(List<PostResponseValidationProcessor> validations) {
        super(validations);
    }

    @Override
    public ValidationResult validate(RequestContext context) {
        return validateAll(context);
    }
}
