package com.restsecure.response.validation;

import com.restsecure.BaseTest;
import com.restsecure.components.deserialize.DefaultJacksonJsonDeserializer;
import com.restsecure.response.HttpResponse;
import com.restsecure.response.Response;
import com.restsecure.response.ResponseBody;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.restsecure.Validations.as;

public class ObjectValidationTest extends BaseTest {
    private User user;

    @BeforeMethod
    public void init() {
        user = new DefaultJacksonJsonDeserializer().deserialize(userJson, User.class);
    }

    @Test
    public void predicateSuccessTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        ResponseValidation validation = as(User.class, user -> user.getId() == 1);
        validation.validate(response);
    }

    @Test
    public void predicateFailTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        ResponseValidation validation = as(User.class, user -> user.getId() == 2);
        expectValidationFailWithErrorText(validation, response, "Wrong value");
    }

    @Test
    public void predicateWithReasonSuccessTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        ResponseValidation validation = as(User.class, user -> user.getId() == 1, "Id validation");
        validation.validate(response);
    }

    @Test
    public void predicateWithReasonFailTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        ResponseValidation validation = as(User.class, user -> user.getId() == 2, "Id validation");

        expectValidationFailWithErrorText(validation, response, "Id validation");
    }
}