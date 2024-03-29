package com.restsecure.validation.composite;

import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.response.validation.ValidationStatus;

public class LogicalOperators {
    public static final Validation AND = (ctx, resp) -> new ValidationResult(ValidationStatus.SUCCESS);
    public static final Validation OR = (ctx, resp) -> new ValidationResult(ValidationStatus.SUCCESS);
}
