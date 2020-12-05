package com.restsecure.validation.composite;

import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationResult;

import java.util.ArrayList;
import java.util.List;

import static com.restsecure.core.response.validation.ValidationStatus.SUCCESS;
import static com.restsecure.validation.composite.LogicalOperators.AND;
import static com.restsecure.validation.composite.LogicalOperators.OR;

public abstract class CompositeValidation implements Validation {

    protected List<Validation> validations;

    public CompositeValidation(List<Validation> validations) {
        this.validations = new ArrayList<>();
        this.validations.addAll(validations);
    }

    @Override
    public abstract ValidationResult validate(Response response);

    protected ValidationResult validateAll(Response response) {
        if (hasLogicalOperators(this.validations)) {
            List<Validation> parsedValidations = parseLogicalOperators(this.validations);

            if (hasLogicalOperators(parsedValidations)) {
                Validation combinedValidation = combineAllInOne(parsedValidations);
                return combinedValidation.validate(response);
            }

            return validateWithoutLogical(response);
        }

        return validateWithoutLogical(response);
    }

    private boolean hasLogicalOperators(List<Validation> validations) {
        return validations.contains(AND) || validations.contains(OR);
    }

    private ValidationResult validateWithoutLogical(Response response) {
        for (Validation validation : this.validations) {

            ValidationResult validationResult = validation.validate(response);

            if (validationResult.isFail()) {
                return validationResult;
            }
        }

        return new ValidationResult(SUCCESS);
    }

    private Validation combineAllInOne(List<Validation> validations) {
        List<Validation> combinedValidations = new ArrayList<>();

        for (int i = 0; i < validations.size(); i++) {

            if (isLogicalOperator(validations.get(i))) {
                combinedValidations.add(new DoubleValidation(validations.get(i - 1), validations.get(i), validations.get(i + 1)));

                if (i + 1 != validations.size() - 1) {
                    for (int j = i + 2; j < validations.size(); j++) {
                        combinedValidations.add(validations.get(j));
                    }

                    break;
                }
            }
        }

        if (combinedValidations.size() > 1) {
            return combineAllInOne(combinedValidations);
        }

        if (combinedValidations.isEmpty()) {
            return response -> new ValidationResult(SUCCESS);
        } else {
            return combinedValidations.get(0);
        }

    }

    private List<Validation> parseLogicalOperators(List<Validation> validations) {

        if (validations.isEmpty()) {
            return validations;
        }

        List<Validation> result = new ArrayList<>();

        for (int i = 0; i < validations.size(); i++) {

            if (isLogicalOperator(validations.get(i))) {
                if (i == validations.size() - 1 && !isLogicalOperator(validations.get(i))) {
                    result.add(validations.get(i));
                    break;
                }

                if (isLogicalOperator(validations.get(i + 1))) {
                    continue;
                }
            } else if (i > 0) {
                if (!isLogicalOperator(validations.get(i - 1))) {
                    result.add(AND);
                }
            }

            result.add(validations.get(i));
        }

        if (!result.isEmpty() && isLogicalOperator(result.get(0))) {
            result.remove(0);
        }

        return result;
    }

    private boolean isLogicalOperator(Validation validation) {
        return validation.equals(AND) || validation.equals(OR);
    }
}