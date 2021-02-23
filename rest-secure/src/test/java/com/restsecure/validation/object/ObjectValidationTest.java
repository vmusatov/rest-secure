package com.restsecure.validation.object;

import com.restsecure.BaseTest;
import com.restsecure.TestObjectMapper;
import com.restsecure.core.response.HttpResponse;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.ResponseBody;
import com.restsecure.core.response.validation.Validation;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.restsecure.Validations.as;

public class ObjectValidationTest extends BaseTest {

    @BeforeMethod
    public void init() {
    }

    @Test
    public void predicateSuccessTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson, new TestObjectMapper()));

        Validation validation = as(User.class, user -> user.getId() == 1);
        expectValidationSuccess(validation, response);
    }

    @Test
    public void predicateFailTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson, new TestObjectMapper()));

        Validation validation = as(User.class, user -> user.getId() == 2);
        expectValidationFailWithErrorText(validation, response, "");
    }

    @Test
    public void predicateWithReasonSuccessTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson, new TestObjectMapper()));

        Validation validation = as(User.class, user -> user.getId() == 1, "Id validation");
        expectValidationSuccess(validation, response);
    }

    @Test
    public void predicateWithReasonFailTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson, new TestObjectMapper()));

        Validation validation = as(User.class, user -> user.getId() == 2, "Id validation");

        expectValidationFailWithErrorText(validation, response, "Id validation");
    }
}