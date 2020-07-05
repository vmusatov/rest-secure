package com.restsecure.response.validation.base;

import com.restsecure.response.Response;
import com.restsecure.response.validation.ResponseValidation;
import com.restsecure.response.validation.ValidationResult;
import com.restsecure.response.validation.ValidationStatus;
import org.hamcrest.Matcher;

import static com.restsecure.response.validation.ValidationStatus.SUCCESS;

public class StatusCodeValidation implements ResponseValidation {

    private final Matcher<Integer> statusCodeMatcher;

    public StatusCodeValidation(Matcher<Integer> statusCodeMatcher) {
        this.statusCodeMatcher = statusCodeMatcher;
    }

    @Override
    public ValidationResult validate(Response response) {

        if (!statusCodeMatcher.matches(response.getStatusCode())) {
            return new ValidationResult(ValidationStatus.FAIL, "Expected status code is " + statusCodeMatcher + ", but found " + response.getStatusCode());
        }

        return new ValidationResult(SUCCESS);
    }
}
