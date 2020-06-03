package com.restsecure.response.validation;

import com.restsecure.BaseTest;
import com.restsecure.http.Cookie;
import com.restsecure.http.Header;
import com.restsecure.response.HttpResponse;
import com.restsecure.response.Response;
import com.restsecure.response.ResponseBody;
import org.testng.annotations.Test;

import static com.restsecure.Matchers.containsPair;
import static org.hamcrest.Matchers.*;

public class ResponseOptionsValidationTest extends BaseTest {

    @Test
    public void intStatusCodeSuccessTest() {
        Response response = new HttpResponse();
        response.setStatusCode(200);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().statusCode(200);

        validation.validate(response);
    }

    @Test
    public void intStatusCodeFailTest() {
        Response response = new HttpResponse();
        response.setStatusCode(500);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().statusCode(200);
        expectValidationFailWithErrorText(validation, response, "Expected status code is <200>");
    }

    @Test
    public void matcherStatusCodeSuccessTest() {
        Response response = new HttpResponse();
        response.setStatusCode(200);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().statusCode(equalTo(200));

        validation.validate(response);
    }

    @Test
    public void matcherStatusCodeFailTest() {
        Response response = new HttpResponse();
        response.setStatusCode(500);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().statusCode(equalTo(200));
        expectValidationFailWithErrorText(validation, response, "Expected status code is <200>");
    }

    @Test
    public void nameAndValueHeaderSuccessTest() {
        Response response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().header("name1", "1");
        validation.validate(response);
    }

    @Test
    public void nameAndValueHeaderFailTest() {
        Response response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().header("name2", "1");
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name2\", \"1\"]");
    }

    @Test
    public void headerSuccessTest() {
        Response response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().header(new Header("name1", "1"));
        validation.validate(response);
    }

    @Test
    public void headerFailTest() {
        Response response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().header(new Header("name2", "1"));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name2\", \"1\"]");
    }

    @Test
    public void nameAndValueMatcherHeaderSuccessTest() {
        Response response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().header("name1", containsString("1"));
        validation.validate(response);
    }

    @Test
    public void nameAndValueMatcherHeaderFailTest() {
        Response response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().header("name1", containsString("2"));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name1\", a string containing \"2\"]");
    }

    @Test
    public void nameAndParsingFunctionAndValueMatcherHeaderSuccessTest() {
        Response response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().header("name1", Integer::parseInt, lessThan(2));
        validation.validate(response);
    }

    @Test
    public void nameAndParsingFunctionAndValueMatcherHeaderFailTest() {
        Response response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().header("name1", Integer::parseInt, lessThan(1));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name1\", a value less than <1>]");
    }

    @Test
    public void headersSuccessTest() {
        Response response = new HttpResponse();
        response.setHeaders(headersWithStringValues);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().headers(containsPair("name1", "value1"));
        validation.validate(response);
    }

    @Test
    public void headersFailTest() {
        Response response = new HttpResponse();
        response.setHeaders(headersWithStringValues);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().headers(containsPair("name1", "wrong value"));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name1\", \"wrong value\"]");
    }

    @Test
    public void nameAndValueCookieSuccessTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().cookie("name1", "1");
        validation.validate(response);
    }

    @Test
    public void nameAndValueCookieFailTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().cookie("name2", "1");
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name2\", \"1\"]");
    }

    @Test
    public void cookieSuccessTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().cookie(new Cookie("name1", "1"));
        validation.validate(response);
    }

    @Test
    public void cookieFailTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().cookie(new Cookie("name2", "1"));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name2\", \"1\"]");
    }

    @Test
    public void nameAndValueMatcherCookieSuccessTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().cookie("name1", containsString("1"));
        validation.validate(response);
    }

    @Test
    public void nameAndValueMatcherCookieFailTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().cookie("name1", containsString("2"));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name1\", a string containing \"2\"]");
    }

    @Test
    public void nameAndParsingFunctionAndValueMatcherCookieSuccessTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().cookie("name1", Integer::parseInt, lessThan(2));
        validation.validate(response);
    }

    @Test
    public void nameAndParsingFunctionAndValueMatcherCookieFailTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithNumberValues);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().cookie("name1", Integer::parseInt, lessThan(1));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name1\", a value less than <1>]");
    }

    @Test
    public void cookiesSuccessTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithStringValues);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().cookies(containsPair("name1", "value1"));
        validation.validate(response);
    }

    @Test
    public void cookiesFailTest() {
        Response response = new HttpResponse();
        response.setCookies(cookiesWithStringValues);

        ResponseOptionsValidation validation = new ResponseOptionsValidation().cookies(containsPair("name1", "wrong value"));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name1\", \"wrong value\"]");
    }

    @Test
    public void bodySuccessTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        ResponseOptionsValidation validation = new ResponseOptionsValidation().body("phone.code", equalTo("+7"));
        validation.validate(response);
    }

    @Test
    public void bodyFailTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        ResponseOptionsValidation validation = new ResponseOptionsValidation().body("phone.code", equalTo("wrong value"));
        expectValidationFailWithErrorText(validation, response, "json path [phone.code] should have value [\"wrong value\"]");
    }
}
