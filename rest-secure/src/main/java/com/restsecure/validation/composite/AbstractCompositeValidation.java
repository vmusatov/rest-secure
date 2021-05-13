package com.restsecure.validation.composite;

import com.restsecure.core.exception.RestSecureException;
import com.restsecure.core.request.RequestContext;
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
    public abstract ValidationResult softValidate(RequestContext context, Response response);

    protected ValidationResult calculateValidationResult(RequestContext context, Response response) {
        if (hasLogicalOperators(this.validations)) {
            List<Validation> parsedValidations = parseLogicalOperators(this.validations);
            return calculateResult(context, response, parsedValidations);
        }

        if (validations.size() == 1) {
            return validations.get(0).softValidate(context, response);
        }

        return calculateWithOneLogical(context, response, AND, this.validations);
    }

    private ValidationResult calculateResult(RequestContext context, Response response, List<Validation> validations) {
        if (!validations.contains(AND)) {
            return calculateWithOneLogical(context, response, OR, validations);
        }

        if (!validations.contains(OR)) {
            return calculateWithOneLogical(context, response, AND, validations);
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

                return calculateResult(context, response, newValidations);
            }
        }

        return new ValidationResult(SUCCESS);
    }

    private ValidationResult calculateWithOneLogical(RequestContext context, Response response, Validation logical, List<Validation> validations) {
        ValidationResult result = new ValidationResult(FAIL);

        for (Validation validation : validations) {
            if (!isLogicalOperator(validation)) {
                result = validation.softValidate(context, response);

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