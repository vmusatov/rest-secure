package com.restsecure.response.validation;

import com.restsecure.BaseTest;
import com.restsecure.deserialize.DefaultJacksonJsonDeserializer;
import com.restsecure.response.HttpResponse;
import com.restsecure.response.Response;
import com.restsecure.response.ResponseBody;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.restsecure.BaseTest.UserMatcher.*;

public class ResponseObjectValidationTest extends BaseTest {
    private User user;

    @BeforeMethod
    public void init() {
        user = new DefaultJacksonJsonDeserializer().deserialize(userJson, User.class);
    }

    @Test
    public void isEqualSuccessTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        ResponseObjectValidation<User> validation = new ResponseObjectValidation<>(User.class).isEquals(user);
        validation.validate(response);
    }

    @Test
    public void isEqualFailTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        user.setLogin("Wrong login");

        ResponseObjectValidation<User> validation = new ResponseObjectValidation<>(User.class).isEquals(user);
        expectValidationFailWithErrorText(validation, response, "Validating value is not equal to");
    }

    @Test
    public void testSuccessTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        ResponseObjectValidation<User> validation = new ResponseObjectValidation<>(User.class).test(resp -> resp.getId().equals(1));
        validation.validate(response);
    }

    @Test
    public void testFailTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        ResponseObjectValidation<User> validation = new ResponseObjectValidation<>(User.class).test(resp -> resp.getId().equals(2));
        expectValidationFailWithErrorText(validation, response, "Wrong value");
    }

    @Test
    public void testWithReasonSuccessTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        ResponseObjectValidation<User> validation = new ResponseObjectValidation<>(User.class)
                .test("Test reason", resp -> resp.getId().equals(1));

        validation.validate(response);
    }

    @Test
    public void testWithReasonFailTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        ResponseObjectValidation<User> validation = new ResponseObjectValidation<>(User.class)
                .test("Test reason", resp -> resp.getId().equals(2));

        expectValidationFailWithErrorText(validation, response, "Test reason");
    }

    @Test
    public void matcherTestSuccessTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        ResponseObjectValidation<User> validation = new ResponseObjectValidation<>(User.class)
                .test(idIs(1), loginIs("UserLogin"), isSubscribe(true));

        validation.validate(response);
    }

    @Test
    public void matcherTestFailTest() {
        Response response = new HttpResponse();
        response.setBody(new ResponseBody(userJson));

        ResponseObjectValidation<User> validation = new ResponseObjectValidation<>(User.class)
                .test(idIs(1), loginIs("Wrong login"), isSubscribe(true));

        expectValidationFailWithErrorText(validation, response, "Expected: User should be <id: anything, login: \"Wrong login\", is_subscribe: anything>");
    }
}
