package com.restsecure.validation.base;

import com.restsecure.BaseTest;
import com.restsecure.core.response.HttpResponse;
import com.restsecure.core.response.MutableResponse;
import com.restsecure.core.response.ResponseBody;
import com.restsecure.core.response.validation.Validation;
import org.testng.annotations.Test;

import static com.restsecure.Validations.body;
import static org.hamcrest.Matchers.equalTo;

public class BodyValidationTest extends BaseTest {

    @Test
    public void bodySuccessTest() {
        MutableResponse response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        Validation validation = body("phone.code", equalTo("+7"));
        expectValidationSuccess(validation, response);
    }

    @Test
    public void bodyFailTest() {
        MutableResponse response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        Validation validation = body("phone.code", equalTo("wrong value"));
        expectValidationFailWithErrorText(validation, response, "json path [phone.code] should have value [\"wrong value\"]");
    }
}
