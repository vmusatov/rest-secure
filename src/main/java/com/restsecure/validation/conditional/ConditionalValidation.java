package com.restsecure.validation.conditional;

import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.ValidationResult;

import static com.restsecure.core.response.validation.ValidationStatus.SUCCESS;

public class ConditionalValidation implements Validation {

    private final Condition condition;
    private final Validation validation;

    public ConditionalValidation(Condition condition, Validation validation) {
        this.condition = condition;
        this.validation = validation;
    }

    @Override
    public ValidationResult validate(RequestContext context, Response response) {
        if (condition.isTrue()) {
            return validation.validate(context, response);
        } else {
            return new ValidationResult(SUCCESS);
        }
    }
}
