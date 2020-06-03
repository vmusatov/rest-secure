package com.restsecure.request.util;

import com.restsecure.RestSecureConfiguration;
import com.restsecure.http.Cookie;
import com.restsecure.http.Header;
import com.restsecure.request.specification.RequestSpecification;
import com.restsecure.request.specification.RequestSpecificationImpl;
import com.restsecure.response.HttpResponse;
import com.restsecure.response.Response;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;

public class SessionTest {

    @Test
    public void sessionTest() {
        Session session = new Session();
        RequestSpecification request = new RequestSpecificationImpl();
        Response response = new HttpResponse();
        response.setCookies(Collections.singletonList(new Cookie(RestSecureConfiguration.getSessionId(), "session_value")));

        session.handleResponse(response);
        session.handleRequest(request);

        Header expectedHeader = new Header("Cookie", RestSecureConfiguration.getSessionId() + "=session_value");
        assertThat("request not contain session cookie", request.getHeaders().contains(expectedHeader));
    }
}
