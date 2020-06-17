package com.restsecure.response.validation;

import com.restsecure.matchers.MatcherUtils;
import com.restsecure.response.Response;
import org.hamcrest.Matcher;

public class HeadersValidation implements ResponseValidation {

    private final String HEADER_VALIDATION_REASON = "Headers validation";

    private final Matcher<? extends Iterable<?>> headersMatcher;

    public HeadersValidation(Matcher<? extends Iterable<?>> headersMatcher) {
        this.headersMatcher = headersMatcher;
    }

    @Override
    public void validate(Response response) {
        MatcherUtils.match(HEADER_VALIDATION_REASON, response.getHeaders(), headersMatcher);
    }
}
