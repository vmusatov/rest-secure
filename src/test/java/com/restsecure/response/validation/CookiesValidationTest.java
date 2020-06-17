package com.restsecure.response.validation;

import com.restsecure.BaseTest;
import com.restsecure.http.Cookie;
import com.restsecure.response.HttpResponse;
import com.restsecure.response.Response;
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

        ResponseValidation validation = cookie("name1", "1");
        validation.validate(response);
    }

    @Test
    public void nameAndValueCookieFailTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        ResponseValidation validation = cookie("name2", "1");
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name2\", \"1\"]");
    }

    @Test
    public void cookieSuccessTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        ResponseValidation validation = cookie(new Cookie("name1", "1"));
        validation.validate(response);
    }

    @Test
    public void cookieFailTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        ResponseValidation validation = cookie(new Cookie("name2", "1"));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name2\", \"1\"]");
    }

    @Test
    public void nameAndValueMatcherCookieSuccessTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        ResponseValidation validation = cookie("name1", containsString("1"));
        validation.validate(response);
    }

    @Test
    public void nameAndValueMatcherCookieFailTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        ResponseValidation validation = cookie("name1", containsString("2"));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name1\", a string containing \"2\"]");
    }

    @Test
    public void nameAndParsingFunctionAndValueMatcherCookieSuccessTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        ResponseValidation validation = cookie("name1", Integer::parseInt, lessThan(2));
        validation.validate(response);
    }

    @Test
    public void nameAndParsingFunctionAndValueMatcherCookieFailTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        ResponseValidation validation = cookie("name1", Integer::parseInt, lessThan(1));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name1\", a value less than <1>]");
    }

    @Test
    public void cookiesSuccessTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithStringValues);

        ResponseValidation validation = cookies(containsPair("name1", "value1"));
        validation.validate(response);
    }

    @Test
    public void cookiesFailTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithStringValues);

        ResponseValidation validation = cookies(containsPair("name1", "wrong value"));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name1\", \"wrong value\"]");
    }
}
