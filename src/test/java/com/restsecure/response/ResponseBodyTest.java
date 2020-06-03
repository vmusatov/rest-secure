package com.restsecure.response;

import com.restsecure.BaseTest;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ResponseBodyTest extends BaseTest {

    @Test
    public void constructorTest() {
        ResponseBody body = new ResponseBody(userJson);
        assertThat(body.getContent(), equalTo(userJson));
    }

    @Test
    public void asTest() {
        ResponseBody body = new ResponseBody(userJson);
        User user = body.as(User.class);

        assertThat(user.getId(), equalTo(1));
        assertThat(user.getLogin(), equalTo("UserLogin"));
        assertThat(user.getIs_subscribed(), equalTo(true));
        assertThat(user.getPhone().getCode(), equalTo("+7"));
        assertThat(user.getPhone().getNumber(), equalTo("9999999999"));
    }

    @Test
    public void getTest() {
        ResponseBody body = new ResponseBody(userJson);

        assertThat(body.get("id"), equalTo(1));
        assertThat(body.get("login"), equalTo("UserLogin"));
        assertThat(body.get("is_subscribed"), equalTo(true));
        assertThat(body.get("phone.code"), equalTo("+7"));
        assertThat(body.get("phone.number"), equalTo("9999999999"));
    }
}
