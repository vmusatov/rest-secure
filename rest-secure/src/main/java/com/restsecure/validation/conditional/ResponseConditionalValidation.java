package com.restsecure.validation.conditional;

import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;

public class ResponseConditionalValidation extends AbstractConditionalValidation {

    private final Validation conditional;

    public ResponseConditionalValidation(Validation conditional, Validation validation, Validation elseValidation) {
        super(validation, elseValidation);
        this.conditional = conditional;
    }

    @Override
    boolean isConditionMet(Response response) {
        return conditional.validate(response).isSuccess();
    }
}
