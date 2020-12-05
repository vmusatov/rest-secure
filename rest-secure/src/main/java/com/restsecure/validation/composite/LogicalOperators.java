package com.restsecure.validation.composite;

import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.response.validation.ValidationStatus;

public class LogicalOperators {
    public static final Validation AND = response -> new ValidationResult(ValidationStatus.SUCCESS);
    public static final Validation OR = response -> new ValidationResult(ValidationStatus.SUCCESS);
}
