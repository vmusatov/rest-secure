package com.restsecure.validation;

import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.response.validation.ValidationStatus;
import com.restsecure.validation.BaseValidation;
import org.hamcrest.Matcher;

import static com.restsecure.core.response.validation.ValidationStatus.SUCCESS;

public class StatusLineValidation extends BaseValidation {

    private final Matcher<String> statusLineMatcher;

    public StatusLineValidation(Matcher<String> statusLineMatcher) {
        this.statusLineMatcher = statusLineMatcher;
    }

    @Override
    public ValidationResult softValidate(Response response) {
        String statusLine = response.getStatusLine();

        if (!statusLineMatcher.matches(statusLine)) {
            return new ValidationResult(ValidationStatus.FAIL, "Expected status line is " + statusLineMatcher + ", but found " + statusLine);
        }

        return new ValidationResult(SUCCESS);
    }
}
