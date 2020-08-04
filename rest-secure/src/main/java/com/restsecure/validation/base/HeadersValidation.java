package com.restsecure.validation.base;

import com.restsecure.core.request.RequestContext;
import com.restsecure.validation.matchers.MatcherUtils;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationResult;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static com.restsecure.core.response.validation.ValidationStatus.FAIL;
import static com.restsecure.core.response.validation.ValidationStatus.SUCCESS;

public class HeadersValidation implements Validation {

    private final String HEADER_VALIDATION_REASON = "Headers validation";

    private final Matcher<? extends Iterable<?>> headersMatcher;

    public HeadersValidation(Matcher<? extends Iterable<?>> headersMatcher) {
        this.headersMatcher = headersMatcher;
    }

    @Override
    public ValidationResult validate(RequestContext context, Response response) {

        if (!headersMatcher.matches(response.getHeaders())) {
            Description description = MatcherUtils.getDescription(HEADER_VALIDATION_REASON, response.getHeaders(), headersMatcher);
            return new ValidationResult(FAIL, description.toString());
        }

        return new ValidationResult(SUCCESS);
    }
}
