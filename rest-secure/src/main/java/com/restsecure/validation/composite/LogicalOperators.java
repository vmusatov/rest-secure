package com.restsecure.validation.composite;

import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.response.validation.ValidationStatus;
import com.restsecure.validation.BaseValidation;

public class LogicalOperators {
    public static final Validation AND = new BaseValidation() {
        @Override
        public ValidationResult softValidate(Response response) {
            return new ValidationResult(ValidationStatus.SUCCESS);
        }
    };

    public static final Validation OR = new BaseValidation() {
        @Override
        public ValidationResult softValidate(Response response) {
            return new ValidationResult(ValidationStatus.SUCCESS);
        }
    };
}
