package com.restsecure.session;

import com.restsecure.core.http.Cookie;
import com.restsecure.core.http.Header;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.HttpResponse;
import com.restsecure.core.response.MutableResponse;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;

public class SessionTest {

    @Test
    public void sessionTest() {
        Session session = new Session();
        MutableResponse response = new HttpResponse();
        response.setCookies(Collections.singletonList(new Cookie(SessionIdNameConfig.DEFAULT_SESSION_ID_NAME, "session_value")));

        RequestContext context = new RequestContext();

        session.processResponse(context, response);
        session.processRequest(context);

        Header expectedHeader = new Header("Cookie", SessionIdNameConfig.DEFAULT_SESSION_ID_NAME + "=session_value");
        assertThat(
                "request not contain session cookie",
                context.getRequestSpec().getHeaders().getFirst(expectedHeader.getName()).equals(expectedHeader.getValue())
        );
    }
}
