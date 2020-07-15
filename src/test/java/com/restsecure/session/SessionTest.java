package com.restsecure.session;

import com.restsecure.core.http.Cookie;
import com.restsecure.core.http.header.Header;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.request.specification.RequestSpecificationImpl;
import com.restsecure.core.response.HttpResponse;
import com.restsecure.core.response.Response;
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

        RequestContext context = new RequestContext(spec);
        context.setResponse(response);

        session.postResponseProcess(context);
        session.preSendProcess(context);

        Header expectedHeader = new Header("Cookie", SessionConfig.DEFAULT_SESSION_ID_NAME + "=session_value");
        assertThat("request not contain session cookie", spec.getHeaders().getFirst(expectedHeader.getName()).equals(expectedHeader.getValue()));
    }
}
