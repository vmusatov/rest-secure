package com.restsecure.validation.base;

import com.restsecure.BaseTest;
import com.restsecure.core.processor.PostResponseValidationProcessor;
import com.restsecure.core.http.Cookie;
import com.restsecure.core.response.HttpResponse;
import com.restsecure.core.response.Response;
import org.testng.annotations.Test;

import static com.restsecure.Matchers.containsPair;
import static com.restsecure.Validations.cookie;
import static com.restsecure.Validations.cookies;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;

public class CookiesValidationTest extends BaseTest {

    @Test
    public void nameAndValueCookieSuccessTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        PostResponseValidationProcessor validation = cookie("name1", "1");
        expectValidationSuccess(validation, response);
    }

    @Test
    public void nameAndValueCookieFailTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        PostResponseValidationProcessor validation = cookie("name2", "1");
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name2\", \"1\"]");
    }

    @Test
    public void cookieSuccessTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        PostResponseValidationProcessor validation = cookie(new Cookie("name1", "1"));
        expectValidationSuccess(validation, response);
    }

    @Test
    public void cookieFailTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        PostResponseValidationProcessor validation = cookie(new Cookie("name2", "1"));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name2\", \"1\"]");
    }

    @Test
    public void nameAndValueMatcherCookieSuccessTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        PostResponseValidationProcessor validation = cookie("name1", containsString("1"));
        expectValidationSuccess(validation, response);
    }

    @Test
    public void nameAndValueMatcherCookieFailTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        PostResponseValidationProcessor validation = cookie("name1", containsString("2"));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name1\", a string containing \"2\"]");
    }

    @Test
    public void nameAndParsingFunctionAndValueMatcherCookieSuccessTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        PostResponseValidationProcessor validation = cookie("name1", Integer::parseInt, lessThan(2));
        expectValidationSuccess(validation, response);
    }

    @Test
    public void nameAndParsingFunctionAndValueMatcherCookieFailTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        PostResponseValidationProcessor validation = cookie("name1", Integer::parseInt, lessThan(1));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name1\", a value less than <1>]");
    }

    @Test
    public void cookiesSuccessTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithStringValues);

        PostResponseValidationProcessor validation = cookies(containsPair("name1", "value1"));
        expectValidationSuccess(validation, response);
    }

    @Test
    public void cookiesFailTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithStringValues);

        PostResponseValidationProcessor validation = cookies(containsPair("name1", "wrong value"));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name1\", \"wrong value\"]");
    }
}
