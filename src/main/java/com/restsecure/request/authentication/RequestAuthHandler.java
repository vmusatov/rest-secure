package com.restsecure.request.authentication;

import com.restsecure.request.RequestHandler;
import com.restsecure.request.specification.RequestSpecification;

/**
 * Base class for request authentication handlers
 */
public abstract class RequestAuthHandler implements RequestHandler {

    /**
     * Subclasses must add authentication
     *
     * @param request request to which authentication must be added
     */
    protected abstract void authenticate(RequestSpecification spec);

    /**
     * Request handling to add authentication
     *
     * @param request request to which authentication must be added
     */
    @Override
    public final void handleRequest(RequestSpecification spec) {
        authenticate(spec);
    }
}
