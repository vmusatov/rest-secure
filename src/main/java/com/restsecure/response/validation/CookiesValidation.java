package com.restsecure.response.validation;

import com.restsecure.matchers.MatcherUtils;
import com.restsecure.response.Response;
import org.hamcrest.Matcher;

public class CookiesValidation implements ResponseValidation {

    private final String COOKIE_VALIDATION_REASON = "Cookies validation";

    private final Matcher<? extends Iterable<?>> cookiesMatcher;

    public CookiesValidation(Matcher<? extends Iterable<?>> cookiesMatcher) {
        this.cookiesMatcher = cookiesMatcher;
    }

    @Override
    public void validate(Response response) {
        MatcherUtils.match(COOKIE_VALIDATION_REASON, response.getCookies(), cookiesMatcher);
    }
}
