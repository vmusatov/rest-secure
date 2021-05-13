package com.restsecure.validation.composite;

import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationResult;

import java.util.List;

import static com.restsecure.core.response.validation.ValidationStatus.SUCCESS;

public class DefaultValidation extends AbstractCompositeValidation {

    public DefaultValidation(List<Validation> validations) {
        super(validations);
    }

    @Override
    public ValidationResult softValidate(RequestContext context, Response response) {
        List<Validation> validations = context.getRequestSpec().getValidations();

        if (hasNoDefaultValidation(validations)) {
            return new ValidationResult(SUCCESS);
        } else {
            if (validations.size() == 1) {
                return calculateValidationResult(context, response);
            } else {
                if (validations.get(validations.size() - 1).equals(this)) {
                    return calculateValidationResult(context, response);
                } else {
                    return new ValidationResult(SUCCESS);
                }
            }
        }
    }

    private boolean isDefaultValidation(Validation validation) {
        return validation instanceof DefaultValidation;
    }

    private boolean hasNoDefaultValidation(List<Validation> validations) {
        for (Validation validation : validations) {
            if (!isDefaultValidation(validation)) {
                return true;
            }
        }

        return false;
    }

}
