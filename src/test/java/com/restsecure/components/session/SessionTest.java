package com.restsecure.components.session;

import com.restsecure.components.session.Session;
import com.restsecure.components.session.SessionConfig;
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
        RequestSpecification spec = new RequestSpecificationImpl();
        Response response = new HttpResponse();
        response.setCookies(Collections.singletonList(new Cookie(SessionConfig.DEFAULT_SESSION_ID_NAME, "session_value")));

        session.handleResponse(response, spec);
        session.handleRequest(spec);

        Header expectedHeader = new Header("Cookie", SessionConfig.DEFAULT_SESSION_ID_NAME + "=session_value");
        assertThat("request not contain session cookie", spec.getHeaders().contains(expectedHeader));
    }
}
