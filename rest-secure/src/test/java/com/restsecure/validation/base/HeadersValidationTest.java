package com.restsecure.validation.base;

import com.restsecure.BaseTest;
import com.restsecure.core.http.Header;
import com.restsecure.core.response.HttpResponse;
import com.restsecure.core.response.MutableResponse;
import com.restsecure.core.response.validation.Validation;
import org.testng.annotations.Test;

import static com.restsecure.Matchers.containsPair;
import static com.restsecure.Validations.header;
import static com.restsecure.Validations.headers;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;

public class HeadersValidationTest extends BaseTest {

    @Test
    public void nameAndValueHeaderSuccessTest() {
        MutableResponse response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        Validation validation = header("name1", "1");
        expectValidationSuccess(validation, response);
    }

    @Test
    public void nameAndValueHeaderFailTest() {
        MutableResponse response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        Validation validation = header("name2", "1");
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name2\", \"1\"]");
    }

    @Test
    public void headerSuccessTest() {
        MutableResponse response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        Validation validation = header(new Header("name1", "1"));
        expectValidationSuccess(validation, response);
    }

    @Test
    public void headerFailTest() {
        MutableResponse response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        Validation validation = header(new Header("name2", "1"));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name2\", \"1\"]");
    }

    @Test
    public void nameAndValueMatcherHeaderSuccessTest() {
        MutableResponse response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        Validation validation = header("name1", containsString("1"));
        expectValidationSuccess(validation, response);
    }

    @Test
    public void nameAndValueMatcherHeaderFailTest() {
        MutableResponse response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        Validation validation = header("name1", containsString("2"));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name1\", a string containing \"2\"]");
    }

    @Test
    public void nameAndParsingFunctionAndValueMatcherHeaderSuccessTest() {
        MutableResponse response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        Validation validation = header("name1", Integer::parseInt, lessThan(2));
        expectValidationSuccess(validation, response);
    }

    @Test
    public void nameAndParsingFunctionAndValueMatcherHeaderFailTest() {
        MutableResponse response = new HttpResponse();
        response.setHeaders(headersWithNumberValues);

        Validation validation = header("name1", Integer::parseInt, lessThan(1));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name1\", a value less than <1>]");
    }

    @Test
    public void headersSuccessTest() {
        MutableResponse response = new HttpResponse();
        response.setHeaders(headersWithStringValues);

        Validation validation = headers(containsPair("name1", "value1"));
        expectValidationSuccess(validation, response);
    }

    @Test
    public void headersFailTest() {
        MutableResponse response = new HttpResponse();
        response.setHeaders(headersWithStringValues);

        Validation validation = headers(containsPair("name1", "wrong value"));
        expectValidationFailWithErrorText(validation, response, "Expected: list containing item [\"name1\", \"wrong value\"]");
    }
}
