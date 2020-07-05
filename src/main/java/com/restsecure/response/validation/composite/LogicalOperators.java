package com.restsecure.response.validation.composite;

import com.restsecure.response.validation.ResponseValidation;
import com.restsecure.response.validation.ValidationResult;
import com.restsecure.response.validation.ValidationStatus;

public class LogicalOperators {
    public static final ResponseValidation AND = response -> new ValidationResult(ValidationStatus.SUCCESS);
    public static final ResponseValidation OR = response -> new ValidationResult(ValidationStatus.SUCCESS);
}
