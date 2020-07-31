package com.restsecure.validation.conditional;

import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationResult;

public abstract class ConditionalValidation implements Validation {

    protected final Validation validation;
    protected final Validation elseValidation;

    public ConditionalValidation(Validation validation, Validation elseValidation) {
        this.validation = validation;
        this.elseValidation = elseValidation;
    }

    abstract boolean isConditionMet(RequestContext context, Response response);

    @Override
    public ValidationResult validate(RequestContext context, Response response) {
        if (isConditionMet(context, response)) {
            return validation.validate(context, response);
        } else {
            return elseValidation.validate(context, response);
        }
    }
}
