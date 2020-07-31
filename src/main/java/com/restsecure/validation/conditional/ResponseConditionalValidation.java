package com.restsecure.validation.conditional;

import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationResult;

public class ResponseConditionalValidation implements Validation {

    private final Validation conditional;
    private final Validation validation;
    private final Validation elseValidation;

    public ResponseConditionalValidation(Validation conditional, Validation validation, Validation elseValidation) {
        this.conditional = conditional;
        this.validation = validation;
        this.elseValidation = elseValidation;
    }

    @Override
    public ValidationResult validate(RequestContext context, Response response) {
        if (!conditional.validate(context, response).isFail()) {
            return validation.validate(context, response);
        } else {
            return elseValidation.validate(context, response);
        }
    }
}
