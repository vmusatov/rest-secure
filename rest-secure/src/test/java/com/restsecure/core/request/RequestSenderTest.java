package com.restsecure.core.request;

import com.restsecure.BaseTest;
import com.restsecure.MockServer;
import com.restsecure.RestSecure;
import com.restsecure.core.http.cookie.Cookie;
import com.restsecure.core.http.header.Header;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.response.validation.Validation;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.restsecure.Matchers.containsPair;
import static com.restsecure.RestSecure.request;
import static com.restsecure.Validations.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RequestSenderTest extends BaseTest {

    private int expectStatusCode = 200;
    private String expectStatusLine = "HTTP/1.1 200 OK";
    private String expectBody = "some body";
    private Header expectHeader = new Header("header", "header_value");
    private Cookie expectCookie = new Cookie("cookie", "cookie_value");

    @BeforeClass
    public void resetGlobalSpec() {
        RestSecure.globalSpecification = request();
    }

    @BeforeMethod
    public void setup() {
        MockServer.reset();
        MockServer.addResponseCookie("cookie", "cookie_value");
        MockServer.addResponseHeader("header", "header_value");
        MockServer.setResponseBody("{ \"data\": \"some body\" }");
    }

    @AfterClass
    public void teardown() {
        MockServer.reset();
    }

    @Test()
    public void sendOneRequestTest() {
        RequestSender.send(
                RestSecure.get(MockServer.GET_PATH).expect(checkMockServerResponse())
        );

        assertThat(MockServer.requestCount, equalTo(1));
    }

    @Test()
    public void sendMultipleRequestsTest() {
        RequestSender.send(
                RestSecure.get(MockServer.GET_PATH)
                        .expect(checkMockServerResponse()),
                RestSecure.post(MockServer.POST_PATH)
                        .expect(checkMockServerResponse()),
                RestSecure.put(MockServer.PUT_PATH)
                        .expect(checkMockServerResponse()),
                RestSecure.delete(MockServer.DELETE_PATH).
                        expect(checkMockServerResponse())
        );

        assertThat(MockServer.requestCount, equalTo(4));
    }

    @Test()
    public void sendRequestsListTest() {
        List<RequestSpecification> requests = Arrays.asList(
                RestSecure.get(MockServer.GET_PATH)
                        .expect(checkMockServerResponse()),
                RestSecure.post(MockServer.POST_PATH)
                        .expect(checkMockServerResponse()),
                RestSecure.put(MockServer.PUT_PATH)
                        .expect(checkMockServerResponse()),
                RestSecure.delete(MockServer.DELETE_PATH).
                        expect(checkMockServerResponse())
        );

        RequestSender.send(requests);

        assertThat(MockServer.requestCount, equalTo(4));
    }

    private Validation checkMockServerResponse() {
        return combine(
                statusCode(expectStatusCode),
                statusLine(expectStatusLine),
                headers(containsPair(expectHeader)),
                cookies(containsPair(expectCookie)),
                body("data", equalTo(expectBody))
        );
    }
}
