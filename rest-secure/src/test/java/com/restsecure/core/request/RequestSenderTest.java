package com.restsecure.core.request;

import com.restsecure.BaseTest;
import com.restsecure.MockServer;
import com.restsecure.RestSecure;
import com.restsecure.core.http.RequestMethod;
import com.restsecure.core.http.cookie.Cookie;
import com.restsecure.core.http.header.Header;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.validation.conditional.ContextCondition;
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
        MockServer.setResponseBody("some body");
    }

    @AfterClass
    public void teardown() {
        MockServer.reset();
    }

    @Test()
    public void sendOneRequestTest() {
        RequestSender.send(RestSecure.get(MockServer.GET_PATH).expect(checkResponse()));
        RequestSender.send(RestSecure.post(MockServer.POST_PATH).expect(checkResponse()));
        RequestSender.send(RestSecure.put(MockServer.PUT_PATH).expect(checkResponse()));
        RequestSender.send(RestSecure.delete(MockServer.DELETE_PATH).expect(checkResponse()));
        RequestSender.send(RestSecure.head(MockServer.HEAD_PATH).expect(checkResponse()));
        RequestSender.send(RestSecure.trace(MockServer.TRACE_PATH).expect(checkResponse()));
        RequestSender.send(RestSecure.options(MockServer.OPTIONS_PATH).expect(checkResponse()));
        RequestSender.send(RestSecure.patch(MockServer.PATCH_PATH).expect(checkResponse()));

        assertThat(MockServer.requestCount, equalTo(8));
    }

    @Test()
    public void sendMultipleRequestsTest() {
        RequestSender.send(
                RestSecure.get(MockServer.GET_PATH).expect(checkResponse()),
                RestSecure.post(MockServer.POST_PATH).expect(checkResponse()),
                RestSecure.put(MockServer.PUT_PATH).expect(checkResponse()),
                RestSecure.delete(MockServer.DELETE_PATH).expect(checkResponse()),
                RestSecure.head(MockServer.HEAD_PATH).expect(checkResponse()),
                RestSecure.trace(MockServer.TRACE_PATH).expect(checkResponse()),
                RestSecure.options(MockServer.OPTIONS_PATH).expect(checkResponse()),
                RestSecure.patch(MockServer.PATCH_PATH).expect(checkResponse())
        );

        assertThat(MockServer.requestCount, equalTo(8));
    }

    @Test()
    public void sendRequestsListTest() {
        List<RequestSpecification> requests = Arrays.asList(
                RestSecure.get(MockServer.GET_PATH).expect(checkResponse()),
                RestSecure.post(MockServer.POST_PATH).expect(checkResponse()),
                RestSecure.put(MockServer.PUT_PATH).expect(checkResponse()),
                RestSecure.delete(MockServer.DELETE_PATH).expect(checkResponse()),
                RestSecure.head(MockServer.HEAD_PATH).expect(checkResponse()),
                RestSecure.trace(MockServer.TRACE_PATH).expect(checkResponse()),
                RestSecure.options(MockServer.OPTIONS_PATH).expect(checkResponse()),
                RestSecure.patch(MockServer.PATCH_PATH).expect(checkResponse())
        );

        RequestSender.send(requests);

        assertThat(MockServer.requestCount, equalTo(8));
    }

    private Validation checkResponse() {
        ContextCondition needCheckBody = ctx -> ctx.getSpecification().getMethod() != RequestMethod.HEAD;

        return combine(
                statusCode(expectStatusCode),
                statusLine(expectStatusLine),
                headers(containsPair(expectHeader)),
                cookies(containsPair(expectCookie)),
                when(needCheckBody, then(
                        body(equalTo(expectBody))
                ))
        );
    }
}
