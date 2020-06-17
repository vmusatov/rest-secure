package com.restsecure.response.validation;

import com.restsecure.BaseTest;
import com.restsecure.response.HttpResponse;
import com.restsecure.response.Response;
import com.restsecure.response.ResponseBody;
import org.testng.annotations.Test;

import static com.restsecure.Validations.body;
import static org.hamcrest.Matchers.equalTo;

public class BodyValidationTest extends BaseTest {

    @Test
    public void bodySuccessTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        ResponseValidation validation = body("phone.code", equalTo("+7"));
        validation.validate(response);
    }

    @Test
    public void bodyFailTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        ResponseValidation validation = body("phone.code", equalTo("wrong value"));
        expectValidationFailWithErrorText(validation, response, "json path [phone.code] should have value [\"wrong value\"]");
    }
}
