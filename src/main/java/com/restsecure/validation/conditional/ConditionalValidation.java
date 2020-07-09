package com.restsecure.validation.conditional;

import com.restsecure.core.request.RequestContext;
import com.restsecure.core.processor.PostResponseValidationProcessor;
import com.restsecure.core.response.validation.ValidationResult;

import static com.restsecure.core.response.validation.ValidationStatus.SUCCESS;

public class ConditionalValidation implements PostResponseValidationProcessor {

    private final Condition condition;
    private final PostResponseValidationProcessor validation;

    public ConditionalValidation(Condition condition, PostResponseValidationProcessor validation) {
        this.condition = condition;
        this.validation = validation;
    }

    @Override
    public ValidationResult validate(RequestContext context) {
        if (condition.isTrue()) {
            return validation.validate(context);
        } else {
            return new ValidationResult(SUCCESS);
        }
    }
}
