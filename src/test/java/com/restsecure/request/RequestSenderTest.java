package com.restsecure.request;

import com.restsecure.BaseTest;
import com.restsecure.GenerateDataHelper;
import com.restsecure.RestSecureConfiguration;
import com.restsecure.request.specification.RequestSpecification;
import com.restsecure.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.restsecure.RestSecure.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RequestSenderTest extends BaseTest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    @BeforeClass
    public void init() {
        RestSecureConfiguration.getDefaultRequestSpecification()
                .auth(bearerToken(""));

        RestSecureConfiguration.setBaseUrl("https://gorest.co.in/public-api");
    }

    @BeforeMethod
    public void initData() {
        firstName = GenerateDataHelper.getRandomString(5);
        lastName = GenerateDataHelper.getRandomString(5);
        email = GenerateDataHelper.getRandomEmail(5);
        phone = GenerateDataHelper.getRandomPhone();
    }

    @AfterClass
    public void reset() {
        RestSecureConfiguration.reset();
    }

    @Test
    public void sendOneRequestTest() {

        Response response = RequestSender.send(
                post("/users")
                        .param("gender", "male")
                        .param("first_name", firstName)
                        .param("last_name", lastName)
                        .param("email", email)
                        .param("phone", phone)
        );

        assertThat(response.getBody().get("result.first_name"), equalTo(firstName));

        deleteUser(response.getBody().get("result.id"));
    }

    @Test
    public void sendMultipleRequestsTest() {

        Response response = RequestSender.send(
                post("/users")
                        .param("gender", "male")
                        .param("first_name", firstName)
                        .param("last_name", lastName)
                        .param("email", email)
                        .param("phone", phone),

                get("/users")
                        .param("first_name", firstName)
        );

        assertThat(response.getBody().get("result[0].first_name"), equalTo(firstName));

        deleteUser(response.getBody().get("result[0].id"));
    }

    @Test
    public void sendRequestsListTest() {

        List<RequestSpecification> requests = Arrays.asList(
                post("/users")
                        .param("gender", "male")
                        .param("first_name", firstName)
                        .param("last_name", lastName)
                        .param("email", email)
                        .param("phone", phone),

                get("/users")
                        .param("first_name", firstName)
        );

        Response response = RequestSender.send(requests);

        assertThat(response.getBody().get("result[0].first_name"), equalTo(firstName));

        deleteUser(response.getBody().get("result[0].id"));
    }

    private void deleteUser(String id) {
        RequestSender.send(
                delete("/users/" + id)
        );
    }
}
