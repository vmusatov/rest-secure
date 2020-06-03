package com.restsecure.request.authentication;

import com.restsecure.request.specification.RequestSpecification;

/**
 * RequestAuthenticationHandler that removes all authentications from the request
 */
public class NoAuthentication extends RequestAuthHandler {

    @Override
    protected void authenticate(RequestSpecification spec) {
        spec.getRequestHandlers().removeIf(handler -> !handler.equals(this) && handler instanceof RequestAuthHandler);
    }
}
