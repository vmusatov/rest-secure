package com.restsecure.validation.composite;

import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationException;
import com.restsecure.core.response.validation.ValidationResult;

import static com.restsecure.core.response.validation.ValidationStatus.FAIL;
import static com.restsecure.core.response.validation.ValidationStatus.SUCCESS;
import static com.restsecure.validation.composite.LogicalOperators.AND;

class DoubleValidation implements Validation {

    private final Validation validation1;
    private final Validation logicalOperator;
    private final Validation validation2;

    public DoubleValidation(Validation validation1, Validation logicalOperator, Validation validation2) {
        this.validation1 = validation1;
        this.logicalOperator = logicalOperator;
        this.validation2 = validation2;
    }

    @Override
    public ValidationResult validate(Response response) {

        ValidationResult result1 = validation1.validate(response);
        ValidationResult result2 = validation2.validate(response);

        if (logicalOperator.equals(AND)) {
            if (result1.isFail()) {
                return result1;
            } else if (result2.isFail()) {
                return result2;
            } else {
                return new ValidationResult(SUCCESS);
            }
        }

        if (logicalOperator.equals(LogicalOperators.OR)) {
            if (result1.isFail() && result2.isFail()) {
                String errorText = result1.getErrorText();
                errorText += "\n\n" + result2.getErrorText();

                return new ValidationResult(FAIL, errorText);
            } else {
                return new ValidationResult(SUCCESS);
            }
        }

        throw new ValidationException("Unknown logical operator");
    }
}
