package com.restsecure.response.validation;

import com.restsecure.BaseTest;
import com.restsecure.response.HttpResponse;
import com.restsecure.response.Response;
import org.testng.annotations.Test;

import static com.restsecure.Validations.statusCode;
import static org.hamcrest.Matchers.equalTo;

public class StatusCodeValidationTest extends BaseTest {

    @Test
    public void intStatusCodeSuccessTest() {
        Response response = new HttpResponse();
        response.setStatusCode(200);

        ResponseValidation validation = statusCode(200);

        validation.validate(response);
    }

    @Test
    public void intStatusCodeFailTest() {
        Response response = new HttpResponse();
        response.setStatusCode(500);

        ResponseValidation validation = statusCode(200);
        expectValidationFailWithErrorText(validation, response, "Expected status code is <200>");
    }

    @Test
    public void matcherStatusCodeSuccessTest() {
        Response response = new HttpResponse();
        response.setStatusCode(200);

        ResponseValidation validation = statusCode(equalTo(200));

        validation.validate(response);
    }

    @Test
    public void matcherStatusCodeFailTest() {
        Response response = new HttpResponse();
        response.setStatusCode(500);

        ResponseValidation validation = statusCode(equalTo(200));
        expectValidationFailWithErrorText(validation, response, "Expected status code is <200>");
    }
}
