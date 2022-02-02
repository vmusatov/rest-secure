package com.restsecure.validation.base;

import com.restsecure.BaseTest;
import com.restsecure.core.response.HttpResponse;
import com.restsecure.core.response.MutableResponse;
import com.restsecure.core.response.validation.Validation;
import org.testng.annotations.Test;

import static com.restsecure.Validations.statusCode;
import static org.hamcrest.Matchers.equalTo;

public class StatusCodeValidationTest extends BaseTest {

    @Test
    public void intStatusCodeSuccessTest() {
        MutableResponse response = new HttpResponse();
        response.setStatusCode(200);

        Validation validation = statusCode(200);

        expectValidationSuccess(validation, response);
    }

    @Test
    public void intStatusCodeFailTest() {
        MutableResponse response = new HttpResponse();
        response.setStatusCode(500);

        Validation validation = statusCode(200);
        expectValidationFailWithErrorText(validation, response, "Expected status code is <200>");
    }

    @Test
    public void matcherStatusCodeSuccessTest() {
        MutableResponse response = new HttpResponse();
        response.setStatusCode(200);

        Validation validation = statusCode(equalTo(200));

        expectValidationSuccess(validation, response);
    }

    @Test
    public void matcherStatusCodeFailTest() {
        MutableResponse response = new HttpResponse();
        response.setStatusCode(500);

        Validation validation = statusCode(equalTo(200));
        expectValidationFailWithErrorText(validation, response, "Expected status code is <200>");
    }
}
