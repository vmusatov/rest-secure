package com.restsecure.response.validation;

import com.restsecure.response.validation.composite.CompositeValidation;

import java.util.List;

public class DefaultValidation extends CompositeValidation {

    public DefaultValidation(List<ResponseValidation> validations) {
        super(validations);
    }
}
