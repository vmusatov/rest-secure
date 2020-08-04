package com.restsecure.validation.base;

import com.restsecure.BaseTest;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.http.header.Header;
import com.restsecure.core.response.HttpResponse;
import com.restsecure.core.response.Response;
import org.testng.annotations.Test;

import static com.restsecure.Matchers.containsPair;
import static com.restsecure.Validations.header;
import static com.restsecure.Validations.headers;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;

public class HeadersValidationTest extends BaseTest {

    @Test
    public void nameAndValueHeaderSuccessTest() {
        Response response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        Validation validation = header("name1", "1");
        expectValidationSuccess(validation, response);
    }

    @Test
    public void nameAndValueHeaderFailTest() {
        Response response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        Validation validation = header("name2", "1");
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name2\", \"1\"]");
    }

    @Test
    public void headerSuccessTest() {
        Response response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        Validation validation = header(new Header("name1", "1"));
        expectValidationSuccess(validation, response);
    }

    @Test
    public void headerFailTest() {
        Response response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        Validation validation = header(new Header("name2", "1"));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name2\", \"1\"]");
    }

    @Test
    public void nameAndValueMatcherHeaderSuccessTest() {
        Response response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        Validation validation = header("name1", containsString("1"));
        expectValidationSuccess(validation, response);
    }

    @Test
    public void nameAndValueMatcherHeaderFailTest() {
        Response response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        Validation validation = header("name1", containsString("2"));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name1\", a string containing \"2\"]");
    }

    @Test
    public void nameAndParsingFunctionAndValueMatcherHeaderSuccessTest() {
        Response response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        Validation validation = header("name1", Integer::parseInt, lessThan(2));
        expectValidationSuccess(validation, response);
    }

    @Test
    public void nameAndParsingFunctionAndValueMatcherHeaderFailTest() {
        Response response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        Validation validation = header("name1", Integer::parseInt, lessThan(1));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name1\", a value less than <1>]");
    }

    @Test
    public void headersSuccessTest() {
        Response response = new HttpResponse();
        response.setHeaders(headersWithStringValues);

        Validation validation = headers(containsPair("name1", "value1"));
        expectValidationSuccess(validation, response);
    }

    @Test
    public void headersFailTest() {
        Response response = new HttpResponse();
        response.setHeaders(headersWithStringValues);

        Validation validation = headers(containsPair("name1", "wrong value"));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name1\", \"wrong value\"]");
    }
}
