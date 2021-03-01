package com.restsecure.authentication;

import com.restsecure.core.http.HeaderNames;
import com.restsecure.core.request.RequestContext;
import org.testng.annotations.Test;

import java.util.Base64;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BasicAuthenticationTest {

    @Test
    public void basicAuthTest() {
        String userName = "username";
        String pass = "pass";
        String encoded = Base64.getEncoder().encodeToString((userName + ":" + pass).getBytes());

        BasicAuthentication auth = new BasicAuthentication(userName, pass);

        RequestContext context = new RequestContext();
        auth.processRequest(context);

        assertThat(context.getSpecification().getHeaders().getFirst(HeaderNames.AUTHORIZATION), equalTo("Basic " + encoded));
    }
}
