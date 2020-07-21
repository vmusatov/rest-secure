package com.restsecure.validation.base;

import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.response.validation.ValidationStatus;
import org.hamcrest.Matcher;

import static com.restsecure.core.response.validation.ValidationStatus.SUCCESS;

public class StatusLineValidation implements Validation {

    private final Matcher<String> statusLineMatcher;

    public StatusLineValidation(Matcher<String> statusLineMatcher) {
        this.statusLineMatcher = statusLineMatcher;
    }

    @Override
    public ValidationResult validate(RequestContext context, Response response) {

        if (!statusLineMatcher.matches(response.getStatusLine())) {
            return new ValidationResult(ValidationStatus.FAIL, "Expected status line is " + statusLineMatcher + ", but found " + response.getStatusLine());
        }

        return new ValidationResult(SUCCESS);
    }
}