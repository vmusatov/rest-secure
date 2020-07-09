package com.restsecure.validation.base;

import com.restsecure.BaseTest;
import com.restsecure.core.response.HttpResponse;
import com.restsecure.core.response.Response;
import com.restsecure.core.processor.PostResponseValidationProcessor;
import org.testng.annotations.Test;

import static com.restsecure.Validations.statusCode;
import static org.hamcrest.Matchers.equalTo;

public class StatusCodeValidationTest extends BaseTest {

    @Test
    public void intStatusCodeSuccessTest() {
        Response response = new HttpResponse();
        response.setStatusCode(200);

        PostResponseValidationProcessor validation = statusCode(200);

        expectValidationSuccess(validation, response);
    }

    @Test
    public void intStatusCodeFailTest() {
        Response response = new HttpResponse();
        response.setStatusCode(500);

        PostResponseValidationProcessor validation = statusCode(200);
        expectValidationFailWithErrorText(validation, response, "Expected status code is <200>");
    }

    @Test
    public void matcherStatusCodeSuccessTest() {
        Response response = new HttpResponse();
        response.setStatusCode(200);

        PostResponseValidationProcessor validation = statusCode(equalTo(200));

        expectValidationSuccess(validation, response);
    }

    @Test
    public void matcherStatusCodeFailTest() {
        Response response = new HttpResponse();
        response.setStatusCode(500);

        PostResponseValidationProcessor validation = statusCode(equalTo(200));
        expectValidationFailWithErrorText(validation, response, "Expected status code is <200>");
    }
}
