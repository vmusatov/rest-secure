package com.restsecure.validation.conditional;

import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;

public class ResponseConditionalValidation extends AbstractConditionalValidation {

    private final Validation conditional;

    public ResponseConditionalValidation(Validation conditional, Validation validation, Validation elseValidation) {
        super(validation, elseValidation);
        this.conditional = conditional;
    }

    @Override
    boolean isConditionMet(RequestContext context, Response response) {
        return conditional.softValidate(context, response).isSuccess();
    }
}
