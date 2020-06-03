package com.restsecure.request.handler;

import com.restsecure.request.RequestHandler;
import com.restsecure.request.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;

/**
 * The general request handler, which is called before sending the request, contains all the internal request handlers
 */
public class GeneralRequestHandler implements RequestHandler {

    private final List<RequestHandler> handlers;

    public GeneralRequestHandler() {
        this.handlers = new ArrayList<>();
    }

    @Override
    public void handleRequest(RequestSpecification spec) {
        for(RequestHandler handler : this.handlers) {
            spec.handleRequest(handler);
        }
    }

    public GeneralRequestHandler addHandler(RequestHandler handler) {
        this.handlers.add(handler);
        return this;
    }
}
