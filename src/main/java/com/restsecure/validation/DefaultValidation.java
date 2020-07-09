package com.restsecure.validation;

import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.response.validation.ValidationStatus;
import com.restsecure.validation.composite.CompositeValidation;
import com.restsecure.core.processor.PostResponseValidationProcessor;

import java.util.List;

import static com.restsecure.core.response.validation.ValidationStatus.SUCCESS;

public class DefaultValidation extends CompositeValidation {

    public DefaultValidation(List<PostResponseValidationProcessor> validations) {
        super(validations);
    }

    @Override
    public ValidationResult validate(RequestContext context) {

        List<PostResponseValidationProcessor> validationProcessors = context.getSpecification().getValidations();

        if(hasNoDefaultValidation(validationProcessors)) {
            return new ValidationResult(SUCCESS);
        } else {
            if(validationProcessors.size() == 1) {
                return validateAll(context);
            } else {
                if(validationProcessors.get(validationProcessors.size() - 1).equals(this)) {
                    return validateAll(context);
                } else {
                    return new ValidationResult(SUCCESS);
                }
            }
        }
    }

    private boolean isDefaultValidation(PostResponseValidationProcessor validation) {
        return validation instanceof DefaultValidation;
    }

    private boolean hasNoDefaultValidation(List<PostResponseValidationProcessor> validationProcessors) {
        for (PostResponseValidationProcessor validation : validationProcessors) {
            if (!isDefaultValidation(validation)) {
                return true;
            }
        }

        return false;
    }

}
