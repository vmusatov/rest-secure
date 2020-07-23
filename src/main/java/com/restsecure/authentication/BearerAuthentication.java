package com.restsecure.authentication;

import com.restsecure.core.http.header.HeaderNames;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;

public class BearerAuthentication implements Processor {

    private final String token;

    public BearerAuthentication(String token) {
        this.token = token;
    }

    @Override
    public void processRequest(RequestContext context) {
        context.getSpecification().header(HeaderNames.AUTHORIZATION, "Bearer " + this.token);
    }
}
