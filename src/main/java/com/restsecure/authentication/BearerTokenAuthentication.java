package com.restsecure.authentication;

import com.restsecure.request.specification.RequestSpecification;

public class BearerTokenAuthentication extends RequestAuthHandler {

    private final String token;

    public BearerTokenAuthentication(String token) {
        this.token = token;
    }

    @Override
    protected void authenticate(RequestSpecification spec) {
        spec.header("Authorization", "Bearer " + this.token);
    }
}
