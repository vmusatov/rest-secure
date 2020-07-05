package com.restsecure.response.validation.composite;

import com.restsecure.response.Response;
import com.restsecure.response.validation.ResponseValidation;
import com.restsecure.response.validation.ResponseValidationsStorage;
import com.restsecure.response.validation.ValidationResult;

import java.util.ArrayList;
import java.util.List;

import static com.restsecure.response.validation.ValidationStatus.SUCCESS;
import static com.restsecure.response.validation.composite.LogicalOperators.AND;
import static com.restsecure.response.validation.composite.LogicalOperators.OR;

public class CompositeValidation implements ResponseValidation {

    protected ResponseValidationsStorage validationsStorage;

    public CompositeValidation(List<ResponseValidation> validations) {
        this.validationsStorage = new ResponseValidationsStorage();
        this.validationsStorage.update(validations);
    }

    @Override
    public ValidationResult validate(Response response) {
        List<ResponseValidation> validations = this.validationsStorage.getAll();

        if (hasLogicalOperators(validations)) {
            List<ResponseValidation> parsedValidations = parseLogicalOperators(validations);

            if (hasLogicalOperators(parsedValidations)) {
                ResponseValidation combinedValidation = combineAllInOne(parsedValidations);
                return combinedValidation.validate(response);
            }

            return validateAll(response);
        }

        return validateAll(response);
    }

    private boolean hasLogicalOperators(List<ResponseValidation> validations) {
        return validations.contains(AND) || validations.contains(OR);
    }

    private ValidationResult validateAll(Response response) {
        for (ResponseValidation validation : this.validationsStorage.getAll()) {

            ValidationResult validationResult = validation.validate(response);

            if (validationResult.isFail()) {
                return validationResult;
            }
        }

        return new ValidationResult(SUCCESS);
    }

    private ResponseValidation combineAllInOne(List<ResponseValidation> validations) {
        List<ResponseValidation> combinedValidations = new ArrayList<>();

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
            return result -> new ValidationResult(SUCCESS);
        } else {
            return combinedValidations.get(0);
        }

    }

    private List<ResponseValidation> parseLogicalOperators(List<ResponseValidation> validations) {

        if (validations.isEmpty()) {
            return validations;
        }

        List<ResponseValidation> result = new ArrayList<>();

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

    private boolean isLogicalOperator(ResponseValidation validation) {
        return validation.equals(AND) || validation.equals(OR);
    }
}