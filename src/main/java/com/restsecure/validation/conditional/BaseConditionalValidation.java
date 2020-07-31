package com.restsecure.validation.conditional;

import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;

public class BaseConditionalValidation extends ConditionalValidation {

    private final Condition condition;

    public BaseConditionalValidation(Condition condition, Validation validation, Validation elseValidation) {
        super(validation, elseValidation);
        this.condition = condition;
    }

    @Override
    boolean isConditionMet(RequestContext context, Response response) {
        return condition.isTrue();
    }
}
