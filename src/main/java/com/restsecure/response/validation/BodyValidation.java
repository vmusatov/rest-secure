package com.restsecure.response.validation;

import com.restsecure.matchers.JsonMatcher;
import com.restsecure.matchers.MatcherUtils;
import com.restsecure.response.Response;

public class BodyValidation implements ResponseValidation {

    private final String BODY_VALIDATION_REASON = "Body validation";

    private final JsonMatcher bodyMatcher;

    public BodyValidation(JsonMatcher bodyMatcher) {
        this.bodyMatcher = bodyMatcher;
    }

    @Override
    public void validate(Response response) {
        MatcherUtils.match(BODY_VALIDATION_REASON, response.getBody().getContent(), bodyMatcher);
    }
}
