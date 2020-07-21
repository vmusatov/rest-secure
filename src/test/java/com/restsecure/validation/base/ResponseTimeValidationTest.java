package com.restsecure.validation.base;

import com.restsecure.BaseTest;
import com.restsecure.core.response.HttpResponse;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static com.restsecure.Validations.time;
import static org.hamcrest.Matchers.lessThan;

public class ResponseTimeValidationTest extends BaseTest {

    @Test
    public void timeValidationTest() {
        Response response = new HttpResponse();
        response.setTime(1000);

        Validation validation = time(lessThan(2000L));

        expectValidationSuccess(validation, response);
    }

    @Test
    public void timeValidationFailTest() {
        Response response = new HttpResponse();
        response.setTime(1000);

        Validation validation = time(lessThan(1000L));

        expectValidationFailWithErrorText(validation, response, "Expected response time is a value less than <1000L>, but found 1000");
    }

    @Test
    public void timeInTimeUnitValidationTest() {
        Response response = new HttpResponse();
        response.setTime(1000);

        Validation validation = time(lessThan(2L), TimeUnit.SECONDS);

        expectValidationSuccess(validation, response);
    }

    @Test
    public void timeInTimeUnitValidationFailTest() {
        Response response = new HttpResponse();
        response.setTime(1000);

        Validation validation = time(lessThan(1L), TimeUnit.SECONDS);

        expectValidationFailWithErrorText(validation, response, "Expected response time is a value less than <1L>, but found 1");
    }
}
