package com.restsecure.validation.base;

import com.restsecure.BaseTest;
import com.restsecure.core.response.HttpResponse;
import com.restsecure.core.response.MutableResponse;
import com.restsecure.core.response.validation.Validation;
import org.testng.annotations.Test;

import static com.restsecure.Validations.statusLine;
import static org.hamcrest.Matchers.containsString;

public class StatusLineValidationTest extends BaseTest {

    @Test
    public void statusLineSuccessTest() {
        MutableResponse response = new HttpResponse();
        response.setStatusLine("status line");

        Validation validation = statusLine("status line");

        expectValidationSuccess(validation, response);
    }

    @Test
    public void statusLineFailTest() {
        MutableResponse response = new HttpResponse();
        response.setStatusLine("wrong");

        Validation validation = statusLine("status line");

        expectValidationFailWithErrorText(validation, response, "Expected status line is \"status line\", but found wrong");
    }

    @Test
    public void matcherStatusLineSuccessTest() {
        MutableResponse response = new HttpResponse();
        response.setStatusLine("status line");

        Validation validation = statusLine(containsString("status line"));

        expectValidationSuccess(validation, response);
    }

    @Test
    public void matcherStatusLineFailTest() {
        MutableResponse response = new HttpResponse();
        response.setStatusLine("wrong");

        Validation validation = statusLine(containsString("status line"));

        expectValidationFailWithErrorText(validation, response, "Expected status line is a string containing \"status line\", but found wrong");
    }
}
