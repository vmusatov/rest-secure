package com.restsecure.validation.composite;

import com.restsecure.core.exception.RestSecureException;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationResult;

import java.util.ArrayList;
import java.util.List;

import static com.restsecure.core.response.validation.ValidationStatus.FAIL;
import static com.restsecure.core.response.validation.ValidationStatus.SUCCESS;
import static com.restsecure.validation.composite.LogicalOperators.AND;
import static com.restsecure.validation.composite.LogicalOperators.OR;

public abstract class AbstractCompositeValidation implements Validation {

    private static final String EXPECTED_VALIDATION_ERROR_TEXT = "Expected validation, but found logical operator";

    protected List<Validation> validations;

    public AbstractCompositeValidation(List<Validation> validations) {
        this.validations = new ArrayList<>();
        this.validations.addAll(validations);
    }

    @Override
    public abstract ValidationResult softValidate(Response response);

    protected ValidationResult calculateValidationResult(Response response) {
        if (hasLogicalOperators(this.validations)) {
            List<Validation> parsedValidations = parseLogicalOperators(this.validations);
            return calculateResult(response, parsedValidations);
        }

        if (validations.size() == 1) {
            return validations.get(0).softValidate(response);
        }

        return calculateWithOneLogical(response, AND, this.validations);
    }

    private ValidationResult calculateResult(Response response, List<Validation> validations) {
        if (!validations.contains(AND)) {
            return calculateWithOneLogical(response, OR, validations);
        }

        if (!validations.contains(OR)) {
            return calculateWithOneLogical(response, AND, validations);
        }

        List<Validation> newValidations = new ArrayList<>();

        for (int i = 0; i < validations.size() - 1; i++) {
            if (validations.get(i) == AND) {
                LogicalValidation logicalValidation = new LogicalValidation(
                        validations.get(i - 1),
                        validations.get(i),
                        validations.get(i + 1)
                );
                if (i == 1) {
                    newValidations.add(logicalValidation);
                    newValidations.addAll(validations.subList(i + 2, validations.size()));
                } else {
                    newValidations.addAll(validations.subList(0, i - 1));
                    newValidations.add(logicalValidation);
                    newValidations.addAll(validations.subList(i + 2, validations.size()));
                }

                return calculateResult(response, newValidations);
            }
        }

        return new ValidationResult(SUCCESS);
    }

    private ValidationResult calculateWithOneLogical(Response response, Validation logical, List<Validation> validations) {
        ValidationResult result = new ValidationResult(FAIL);

        for (Validation validation : validations) {
            if (!isLogicalOperator(validation)) {
                result = validation.softValidate(response);

                if (logical == OR && result.getStatus() == SUCCESS) {
                    return result;
                }

                if (logical == AND && result.getStatus() == FAIL) {
                    return result;
                }
            }
        }

        return result;
    }

    private List<Validation> parseLogicalOperators(List<Validation> validations) {

        if (validations.isEmpty()) {
            return validations;
        }

        if (isLogicalOperator(validations.get(0))) {
            throw new RestSecureException(EXPECTED_VALIDATION_ERROR_TEXT);
        }

        if (isLogicalOperator(validations.get(validations.size() - 1))) {
            throw new RestSecureException(EXPECTED_VALIDATION_ERROR_TEXT);
        }

        List<Validation> result = new ArrayList<>();

        for (int i = 0; i < validations.size(); i++) {
            if (isLogicalOperator(validations.get(i)) && isLogicalOperator(validations.get(i + 1))) {
                throw new RestSecureException(EXPECTED_VALIDATION_ERROR_TEXT);
            }
            if (i > 0 && !isLogicalOperator(validations.get(i)) && !isLogicalOperator(validations.get(i - 1))) {
                result.add(AND);
            }
            result.add(validations.get(i));
        }

        return result;
    }

    private boolean hasLogicalOperators(List<Validation> validations) {
        return validations.contains(AND) || validations.contains(OR);
    }

    private boolean isLogicalOperator(Validation validation) {
        return validation.equals(AND) || validation.equals(OR);
    }
}