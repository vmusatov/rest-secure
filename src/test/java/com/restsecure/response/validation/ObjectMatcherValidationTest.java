package com.restsecure.response.validation;

import com.restsecure.BaseTest;
import com.restsecure.response.HttpResponse;
import com.restsecure.response.Response;
import com.restsecure.response.ResponseBody;
import org.testng.annotations.Test;

import static com.restsecure.BaseTest.UserMatcher.*;
import static com.restsecure.Validations.as;

public class ObjectMatcherValidationTest extends BaseTest {

    @Test
    public void matcherSuccessTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        ResponseValidation validation = as(User.class, idIs(1), loginIs("UserLogin"), isSubscribe(true));

        validation.validate(response);
    }

    @Test
    public void matcherFailTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        ResponseValidation validation = as(User.class, idIs(1), loginIs("Wrong login"), isSubscribe(true));


        expectValidationFailWithErrorText(validation, response, "Expected: User should be <id: anything, login: \"Wrong login\", is_subscribe: anything>");
    }
}
