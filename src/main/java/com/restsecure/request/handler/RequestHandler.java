package com.restsecure.request.handler;

import com.restsecure.request.specification.RequestSpecification;

/**
 * Allows you to handle request and get some information about request or change request
 */
public interface RequestHandler {
    void handleRequest(RequestSpecification spec);
}
