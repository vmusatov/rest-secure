package com.restsecure.response.validation;

import com.restsecure.response.Response;
import org.hamcrest.Matcher;

public class StatusCodeValidation implements ResponseValidation {

    private final Matcher<Integer> statusCodeMatcher;

    public StatusCodeValidation(Matcher<Integer> statusCodeMatcher) {
        this.statusCodeMatcher = statusCodeMatcher;
    }

    @Override
    public void validate(Response response) {
        if (!statusCodeMatcher.matches(response.getStatusCode())) {
            throw new AssertionError("Expected status code is " + statusCodeMatcher + ", but found " + response.getStatusCode());
        }
    }
}
