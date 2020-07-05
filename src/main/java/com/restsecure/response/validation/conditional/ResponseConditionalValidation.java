package com.restsecure.response.validation.conditional;

import com.restsecure.response.Response;
import com.restsecure.response.validation.ResponseValidation;
import com.restsecure.response.validation.ValidationResult;

import static com.restsecure.response.validation.ValidationStatus.SUCCESS;

public class ResponseConditionalValidation implements ResponseValidation {

    private final ResponseValidation conditional;
    private final ResponseValidation validation;

    public ResponseConditionalValidation(ResponseValidation conditional, ResponseValidation validation) {
        this.conditional = conditional;
        this.validation = validation;
    }

    @Override
    public ValidationResult validate(Response response) {
        if (!conditional.validate(response).isFail()) {
            return validation.validate(response);
        } else {
            return new ValidationResult(SUCCESS);
        }
    }
}
