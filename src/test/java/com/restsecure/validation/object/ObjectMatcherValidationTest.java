package com.restsecure.validation.object;

import com.restsecure.BaseTest;
import com.restsecure.core.response.HttpResponse;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.ResponseBody;
import com.restsecure.core.response.validation.Validation;
import org.testng.annotations.Test;

import static com.restsecure.BaseTest.UserMatcher.*;
import static com.restsecure.Validations.as;

public class ObjectMatcherValidationTest extends BaseTest {

    @Test
    public void matcherSuccessTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        Validation validation = as(User.class, idIs(1), loginIs("UserLogin"), isSubscribe(true));

        expectValidationSuccess(validation, response);
    }

    @Test
    public void matcherFailTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        Validation validation = as(User.class, idIs(1), loginIs("Wrong login"), isSubscribe(true));


        expectValidationFailWithErrorText(validation, response, "Expected: User should be <id: anything, login: \"Wrong login\", is_subscribe: anything>");
    }
}
