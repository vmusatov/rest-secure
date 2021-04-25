package com.restsecure.validation;

import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.response.validation.ValidationStatus;
import com.restsecure.validation.BaseValidation;
import org.hamcrest.Matcher;

import static com.restsecure.core.response.validation.ValidationStatus.SUCCESS;

public class StatusCodeValidation extends BaseValidation {

    private final Matcher<Integer> statusCodeMatcher;

    public StatusCodeValidation(Matcher<Integer> statusCodeMatcher) {
        this.statusCodeMatcher = statusCodeMatcher;
    }

    @Override
    public ValidationResult softValidate(Response response) {
        int statusCode = response.getStatusCode();

        if (!statusCodeMatcher.matches(statusCode)) {
            return new ValidationResult(ValidationStatus.FAIL, "Expected status code is " + statusCodeMatcher + ", but found " + statusCode);
        }

        return new ValidationResult(SUCCESS);
    }
}
