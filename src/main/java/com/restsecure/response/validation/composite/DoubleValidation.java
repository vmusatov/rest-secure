package com.restsecure.response.validation.composite;

import com.restsecure.response.Response;
import com.restsecure.response.validation.ResponseValidation;
import com.restsecure.response.validation.ValidationException;
import com.restsecure.response.validation.ValidationResult;

import static com.restsecure.response.validation.ValidationStatus.FAIL;
import static com.restsecure.response.validation.ValidationStatus.SUCCESS;
import static com.restsecure.response.validation.composite.LogicalOperators.AND;

class DoubleValidation implements ResponseValidation {

    private final ResponseValidation validation1;
    private final ResponseValidation logicalOperator;
    private final ResponseValidation validation2;

    public DoubleValidation(ResponseValidation validation1, ResponseValidation logicalOperator, ResponseValidation validation2) {
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
