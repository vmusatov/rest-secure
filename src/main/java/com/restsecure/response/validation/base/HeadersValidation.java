package com.restsecure.response.validation.base;

import com.restsecure.matchers.MatcherUtils;
import com.restsecure.response.Response;
import com.restsecure.response.validation.ResponseValidation;
import com.restsecure.response.validation.ValidationResult;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static com.restsecure.response.validation.ValidationStatus.FAIL;
import static com.restsecure.response.validation.ValidationStatus.SUCCESS;

public class HeadersValidation implements ResponseValidation {

    private final String HEADER_VALIDATION_REASON = "Headers validation";

    private final Matcher<? extends Iterable<?>> headersMatcher;

    public HeadersValidation(Matcher<? extends Iterable<?>> headersMatcher) {
        this.headersMatcher = headersMatcher;
    }

    @Override
    public ValidationResult validate(Response response) {

        if (!headersMatcher.matches(response.getHeaders())) {
            Description description = MatcherUtils.getDescription(HEADER_VALIDATION_REASON, response.getHeaders(), headersMatcher);
            return new ValidationResult(FAIL, description.toString());
        }

        return new ValidationResult(SUCCESS);
    }
}
