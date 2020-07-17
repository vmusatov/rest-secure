package com.restsecure.validation.conditional;

import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.response.validation.ValidationStatus;

public class ResponseConditionalValidation implements Validation {

    private final Validation conditional;
    private final Validation validation;

    public ResponseConditionalValidation(Validation conditional, Validation validation) {
        this.conditional = conditional;
        this.validation = validation;
    }

    @Override
    public ValidationResult validate(RequestContext context, Response response) {

        if (!conditional.validate(context, response).isFail()) {
            return validation.validate(context, response);
        } else {
            return new ValidationResult(ValidationStatus.SUCCESS);
        }
    }
}
