package com.restsecure.validation;

import com.restsecure.core.http.Cookie;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.util.NameValueList;
import com.restsecure.validation.matchers.MatcherUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static com.restsecure.core.response.validation.ValidationStatus.FAIL;
import static com.restsecure.core.response.validation.ValidationStatus.SUCCESS;

public class CookiesValidation implements Validation {

    private final String COOKIE_VALIDATION_REASON = "Cookies validation";

    private final Matcher<? extends Iterable<?>> cookiesMatcher;

    public CookiesValidation(Matcher<? extends Iterable<?>> cookiesMatcher) {
        this.cookiesMatcher = cookiesMatcher;
    }

    @Override
    public ValidationResult softValidate(Response response) {
        NameValueList<Cookie> cookies = response.getCookies();

        if (!cookiesMatcher.matches(cookies)) {
            Description description = MatcherUtils.getDescription(COOKIE_VALIDATION_REASON, cookies, cookiesMatcher);
            return new ValidationResult(FAIL, description.toString());
        }

        return new ValidationResult(SUCCESS);
    }
}
