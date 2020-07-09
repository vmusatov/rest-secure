package com.restsecure.validation.composite;

import com.restsecure.core.processor.PostResponseValidationProcessor;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.response.validation.ValidationStatus;

public class LogicalOperators {
    public static final PostResponseValidationProcessor AND = response -> new ValidationResult(ValidationStatus.SUCCESS);
    public static final PostResponseValidationProcessor OR = response -> new ValidationResult(ValidationStatus.SUCCESS);
}
