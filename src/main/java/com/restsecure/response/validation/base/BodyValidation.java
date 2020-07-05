package com.restsecure.response.validation.base;

import com.restsecure.matchers.JsonMatcher;
import com.restsecure.matchers.MatcherUtils;
import com.restsecure.response.Response;
import com.restsecure.response.validation.ResponseValidation;
import com.restsecure.response.validation.ValidationResult;
import org.hamcrest.Description;

import static com.restsecure.response.validation.ValidationStatus.FAIL;
import static com.restsecure.response.validation.ValidationStatus.SUCCESS;

public class BodyValidation implements ResponseValidation {

    private final String BODY_VALIDATION_REASON = "Body validation";

    private final JsonMatcher bodyMatcher;

    public BodyValidation(JsonMatcher bodyMatcher) {
        this.bodyMatcher = bodyMatcher;
    }

    @Override
    public ValidationResult validate(Response response) {

        if (!bodyMatcher.matches(response.getBody().getContent())) {
            Description description = MatcherUtils.getDescription(BODY_VALIDATION_REASON, response.getBody().getContent(), bodyMatcher);
            return new ValidationResult(FAIL, description.toString());
        }

        return new ValidationResult(SUCCESS);
    }
}
