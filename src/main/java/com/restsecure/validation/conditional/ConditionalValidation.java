package com.restsecure.validation.conditional;

import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationResult;

public class ConditionalValidation implements Validation {

    private final Condition condition;
    private final Validation validation;
    private final Validation elseValidation;

    public ConditionalValidation(Condition condition, Validation validation, Validation elseValidation) {
        this.condition = condition;
        this.validation = validation;
        this.elseValidation = elseValidation;
    }

    @Override
    public ValidationResult validate(RequestContext context, Response response) {
        if (condition.isTrue()) {
            return validation.validate(context, response);
        } else {
            return elseValidation.validate(context, response);
        }
    }
}
