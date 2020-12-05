package com.restsecure.validation.conditional;

import com.restsecure.core.condition.Condition;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;

public class ConditionalValidation extends AbstractConditionalValidation {

    private final Condition condition;

    public ConditionalValidation(Condition condition, Validation validation, Validation elseValidation) {
        super(validation, elseValidation);
        this.condition = condition;
    }

    @Override
    boolean isConditionMet(Response response) {
        return condition.isTrue();
    }
}
