package com.restsecure.response.validation.conditional;

import com.restsecure.response.Response;
import com.restsecure.response.validation.ResponseValidation;
import com.restsecure.response.validation.ValidationResult;

import static com.restsecure.response.validation.ValidationStatus.SUCCESS;

public class ConditionalValidation implements ResponseValidation {

    private final Condition condition;
    private final ResponseValidation validation;

    public ConditionalValidation(Condition condition, ResponseValidation validation) {
        this.condition = condition;
        this.validation = validation;
    }

    @Override
    public ValidationResult validate(Response response) {
        if (condition.isTrue()) {
            return validation.validate(response);
        } else {
            return new ValidationResult(SUCCESS);
        }
    }
}
