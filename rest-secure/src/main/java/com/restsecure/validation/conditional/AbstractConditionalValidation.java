package com.restsecure.validation.conditional;

import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.validation.BaseValidation;

public abstract class AbstractConditionalValidation extends BaseValidation {

    protected final Validation validation;
    protected final Validation elseValidation;

    public AbstractConditionalValidation(Validation validation, Validation elseValidation) {
        this.validation = validation;
        this.elseValidation = elseValidation;
    }

    abstract boolean isConditionMet(Response response);

    @Override
    public ValidationResult softValidate(Response response) {
        if (isConditionMet(response)) {
            return validation.softValidate(response);
        } else {
            return elseValidation.softValidate(response);
        }
    }
}
