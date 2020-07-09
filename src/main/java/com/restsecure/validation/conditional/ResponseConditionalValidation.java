package com.restsecure.validation.conditional;

import com.restsecure.core.processor.PostResponseValidationProcessor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.response.validation.ValidationStatus;

public class ResponseConditionalValidation implements PostResponseValidationProcessor {

    private final PostResponseValidationProcessor conditional;
    private final PostResponseValidationProcessor validation;

    public ResponseConditionalValidation(PostResponseValidationProcessor conditional, PostResponseValidationProcessor validation) {
        this.conditional = conditional;
        this.validation = validation;
    }

    @Override
    public ValidationResult validate(RequestContext context) {

        if (!conditional.validate(context).isFail()) {
            return validation.validate(context);
        } else {
            return new ValidationResult(ValidationStatus.SUCCESS);
        }
    }
}
