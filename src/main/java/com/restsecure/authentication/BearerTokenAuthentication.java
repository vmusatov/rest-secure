package com.restsecure.authentication;

import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;

public class BearerTokenAuthentication implements Processor {

    private final String token;

    public BearerTokenAuthentication(String token) {
        this.token = token;
    }

    @Override
    public void processRequest(RequestContext context) {
        context.getSpecification().header("Authorization", "Bearer " + this.token);
    }
}
