package com.restsecure.response.validation.base;

import com.restsecure.matchers.MatcherUtils;
import com.restsecure.response.Response;
import com.restsecure.response.validation.ResponseValidation;
import com.restsecure.response.validation.ValidationResult;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static com.restsecure.response.validation.ValidationStatus.FAIL;
import static com.restsecure.response.validation.ValidationStatus.SUCCESS;

public class CookiesValidation implements ResponseValidation {

    private final String COOKIE_VALIDATION_REASON = "Cookies validation";

    private final Matcher<? extends Iterable<?>> cookiesMatcher;

    public CookiesValidation(Matcher<? extends Iterable<?>> cookiesMatcher) {
        this.cookiesMatcher = cookiesMatcher;
    }

    @Override
    public ValidationResult validate(Response response) {

        if (!cookiesMatcher.matches(response.getCookies())) {
            Description description = MatcherUtils.getDescription(COOKIE_VALIDATION_REASON, response.getCookies(), cookiesMatcher);
            return new ValidationResult(FAIL, description.toString());
        }

        return new ValidationResult(SUCCESS);
    }
}
