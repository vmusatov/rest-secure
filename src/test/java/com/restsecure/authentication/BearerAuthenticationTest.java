package com.restsecure.authentication;

import com.restsecure.core.http.header.HeaderNames;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.request.specification.RequestSpecificationImpl;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BearerAuthenticationTest {

    @Test
    public void bearerAuthTest() {
        String token = "my_token";

        BearerAuthentication auth = new BearerAuthentication(token);
        RequestSpecification spec = new RequestSpecificationImpl();
        auth.processRequest(new RequestContext(spec));

        assertThat(spec.getHeaders().getFirst(HeaderNames.AUTHORIZATION), equalTo("Bearer " + token));
    }
}
