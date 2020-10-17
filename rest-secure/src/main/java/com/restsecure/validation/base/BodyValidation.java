package com.restsecure.validation.base;

import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.validation.matchers.JsonMatcher;
import com.restsecure.validation.matchers.MatcherUtils;
import org.hamcrest.Description;

import static com.restsecure.core.response.validation.ValidationStatus.FAIL;
import static com.restsecure.core.response.validation.ValidationStatus.SUCCESS;

public class BodyValidation implements Validation {

    private final String BODY_VALIDATION_REASON = "Body validation";

    private final JsonMatcher bodyMatcher;

    public BodyValidation(JsonMatcher bodyMatcher) {
        this.bodyMatcher = bodyMatcher;
    }

    @Override
    public ValidationResult validate(RequestContext context, Response response) {
        String body = response.getBody().asString();

        if (!bodyMatcher.matches(body)) {
            Description description = MatcherUtils.getDescription(BODY_VALIDATION_REASON, body, bodyMatcher);
            return new ValidationResult(FAIL, description.toString());
        }

        return new ValidationResult(SUCCESS);
    }
}