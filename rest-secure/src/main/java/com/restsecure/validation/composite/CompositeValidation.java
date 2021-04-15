package com.restsecure.validation.composite;

import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationResult;

import java.util.List;

public class CompositeValidation extends AbstractCompositeValidation {

    public CompositeValidation(List<Validation> validations) {
        super(validations);
    }

    @Override
    public ValidationResult softValidate(Response response) {
        return calculateValidationResult(response);
    }
}
