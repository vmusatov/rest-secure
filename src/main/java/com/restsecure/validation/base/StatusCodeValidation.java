package com.restsecure.validation.base;

import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.Response;
import com.restsecure.core.processor.PostResponseValidationProcessor;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.response.validation.ValidationStatus;
import org.hamcrest.Matcher;

import static com.restsecure.core.response.validation.ValidationStatus.SUCCESS;

public class StatusCodeValidation implements PostResponseValidationProcessor {

    private final Matcher<Integer> statusCodeMatcher;

    public StatusCodeValidation(Matcher<Integer> statusCodeMatcher) {
        this.statusCodeMatcher = statusCodeMatcher;
    }

    @Override
    public ValidationResult validate(RequestContext context) {
        Response response = context.getResponse();

        if (!statusCodeMatcher.matches(response.getStatusCode())) {
            return new ValidationResult(ValidationStatus.FAIL, "Expected status code is " + statusCodeMatcher + ", but found " + response.getStatusCode());
        }

        return new ValidationResult(SUCCESS);
    }
}
