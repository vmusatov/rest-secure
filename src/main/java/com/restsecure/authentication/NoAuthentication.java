package com.restsecure.authentication;

import com.restsecure.request.specification.RequestSpecification;

/**
 * RequestAuthenticationHandler that do nothing
 */
public class NoAuthentication extends RequestAuthHandler {

    @Override
    protected void authenticate(RequestSpecification spec) {

    }
}
