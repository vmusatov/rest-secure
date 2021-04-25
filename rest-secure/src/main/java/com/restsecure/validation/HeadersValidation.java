package com.restsecure.validation;

import com.restsecure.core.http.Header;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.util.NameValueList;
import com.restsecure.validation.BaseValidation;
import com.restsecure.validation.matchers.MatcherUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static com.restsecure.core.response.validation.ValidationStatus.FAIL;
import static com.restsecure.core.response.validation.ValidationStatus.SUCCESS;

public class HeadersValidation extends BaseValidation {

    private final String HEADER_VALIDATION_REASON = "Headers validation";

    private final Matcher<? extends Iterable<?>> headersMatcher;

    public HeadersValidation(Matcher<? extends Iterable<?>> headersMatcher) {
        this.headersMatcher = headersMatcher;
    }

    @Override
    public ValidationResult softValidate(Response response) {
        NameValueList<Header> headers = response.getHeaders();

        if (!headersMatcher.matches(headers)) {
            Description description = MatcherUtils.getDescription(HEADER_VALIDATION_REASON, headers, headersMatcher);
            return new ValidationResult(FAIL, description.toString());
        }

        return new ValidationResult(SUCCESS);
    }
}
