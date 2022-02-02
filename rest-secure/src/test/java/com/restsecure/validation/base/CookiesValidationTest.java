package com.restsecure.validation.base;

import com.restsecure.BaseTest;
import com.restsecure.core.http.Cookie;
import com.restsecure.core.response.HttpResponse;
import com.restsecure.core.response.MutableResponse;
import com.restsecure.core.response.validation.Validation;
import org.testng.annotations.Test;

import static com.restsecure.Matchers.containsPair;
import static com.restsecure.Validations.cookie;
import static com.restsecure.Validations.cookies;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;

public class CookiesValidationTest extends BaseTest {

    @Test
    public void nameAndValueCookieSuccessTest() {
        MutableResponse response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        Validation validation = cookie("name1", "1");
        expectValidationSuccess(validation, response);
    }

    @Test
    public void nameAndValueCookieFailTest() {
        MutableResponse response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        Validation validation = cookie("name2", "1");
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name2\", \"1\"]");
    }

    @Test
    public void cookieSuccessTest() {
        MutableResponse response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        Validation validation = cookie(new Cookie("name1", "1"));
        expectValidationSuccess(validation, response);
    }

    @Test
    public void cookieFailTest() {
        MutableResponse response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        Validation validation = cookie(new Cookie("name2", "1"));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name2\", \"1\"]");
    }

    @Test
    public void nameAndValueMatcherCookieSuccessTest() {
        MutableResponse response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        Validation validation = cookie("name1", containsString("1"));
        expectValidationSuccess(validation, response);
    }

    @Test
    public void nameAndValueMatcherCookieFailTest() {
        MutableResponse response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        Validation validation = cookie("name1", containsString("2"));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name1\", a string containing \"2\"]");
    }

    @Test
    public void nameAndParsingFunctionAndValueMatcherCookieSuccessTest() {
        MutableResponse response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        Validation validation = cookie("name1", Integer::parseInt, lessThan(2));
        expectValidationSuccess(validation, response);
    }

    @Test
    public void nameAndParsingFunctionAndValueMatcherCookieFailTest() {
        MutableResponse response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        Validation validation = cookie("name1", Integer::parseInt, lessThan(1));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name1\", a value less than <1>]");
    }

    @Test
    public void cookiesSuccessTest() {
        MutableResponse response = new HttpResponse();
        response.setCookies(cookiesWithStringValues);

        Validation validation = cookies(containsPair("name1", "value1"));
        expectValidationSuccess(validation, response);
    }

    @Test
    public void cookiesFailTest() {
        MutableResponse response = new HttpResponse();
        response.setCookies(cookiesWithStringValues);

        Validation validation = cookies(containsPair("name1", "wrong value"));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name1\", \"wrong value\"]");
    }
}
