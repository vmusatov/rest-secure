package com.restsecure.validation.base;

import com.restsecure.core.processor.PostResponseValidationProcessor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.validation.matchers.MatcherUtils;
import com.restsecure.core.response.Response;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static com.restsecure.core.response.validation.ValidationStatus.FAIL;
import static com.restsecure.core.response.validation.ValidationStatus.SUCCESS;

public class CookiesValidation implements PostResponseValidationProcessor {

    private final String COOKIE_VALIDATION_REASON = "Cookies validation";

    private final Matcher<? extends Iterable<?>> cookiesMatcher;

    public CookiesValidation(Matcher<? extends Iterable<?>> cookiesMatcher) {
        this.cookiesMatcher = cookiesMatcher;
    }

    @Override
    public ValidationResult validate(RequestContext context) {
        Response response = context.getResponse();

        if (!cookiesMatcher.matches(response.getCookies())) {
            Description description = MatcherUtils.getDescription(COOKIE_VALIDATION_REASON, response.getCookies(), cookiesMatcher);
            return new ValidationResult(FAIL, description.toString());
        }

        return new ValidationResult(SUCCESS);
    }
}
