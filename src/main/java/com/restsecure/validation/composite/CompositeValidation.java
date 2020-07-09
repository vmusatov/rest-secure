package com.restsecure.validation.composite;

import com.restsecure.core.processor.PostResponseValidationProcessor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.validation.ValidationResult;

import java.util.ArrayList;
import java.util.List;

import static com.restsecure.core.response.validation.ValidationStatus.SUCCESS;
import static com.restsecure.validation.composite.LogicalOperators.AND;
import static com.restsecure.validation.composite.LogicalOperators.OR;

public abstract class CompositeValidation implements PostResponseValidationProcessor {

    protected List<PostResponseValidationProcessor> validationProcessors;

    public CompositeValidation(List<PostResponseValidationProcessor> validations) {
        this.validationProcessors = new ArrayList<>();
        this.validationProcessors.addAll(validations);
    }

    @Override
    public abstract ValidationResult validate(RequestContext context);

    protected ValidationResult validateAll(RequestContext context) {
        if (hasLogicalOperators(this.validationProcessors)) {
            List<PostResponseValidationProcessor> parsedValidations = parseLogicalOperators(this.validationProcessors);

            if (hasLogicalOperators(parsedValidations)) {
                PostResponseValidationProcessor combinedValidation = combineAllInOne(parsedValidations);
                return combinedValidation.validate(context);
            }

            return validateWithoutLogical(context);
        }

        return validateWithoutLogical(context);
    }

    private boolean hasLogicalOperators(List<PostResponseValidationProcessor> validations) {
        return validations.contains(AND) || validations.contains(OR);
    }

    private ValidationResult validateWithoutLogical(RequestContext context) {
        for (PostResponseValidationProcessor validation : this.validationProcessors) {

            ValidationResult validationResult = validation.validate(context);

            if (validationResult.isFail()) {
                return validationResult;
            }
        }

        return new ValidationResult(SUCCESS);
    }

    private PostResponseValidationProcessor combineAllInOne(List<PostResponseValidationProcessor> validations) {
        List<PostResponseValidationProcessor> combinedValidations = new ArrayList<>();

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

    private List<PostResponseValidationProcessor> parseLogicalOperators(List<PostResponseValidationProcessor> validations) {

        if (validations.isEmpty()) {
            return validations;
        }

        List<PostResponseValidationProcessor> result = new ArrayList<>();

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

    private boolean isLogicalOperator(PostResponseValidationProcessor validation) {
        return validation.equals(AND) || validation.equals(OR);
    }
}