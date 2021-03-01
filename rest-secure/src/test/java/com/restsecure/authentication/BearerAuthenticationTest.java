package com.restsecure.authentication;

import com.restsecure.core.http.HeaderNames;
import com.restsecure.core.request.RequestContext;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BearerAuthenticationTest {

    @Test
    public void bearerAuthTest() {
        String token = "my_token";

        BearerAuthentication auth = new BearerAuthentication(token);

        RequestContext context = new RequestContext();
        auth.processRequest(context);

        assertThat(context.getSpecification().getHeaders().getFirst(HeaderNames.AUTHORIZATION), equalTo("Bearer " + token));
    }
}
