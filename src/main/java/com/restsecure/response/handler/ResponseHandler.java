package com.restsecure.response.handler;

import com.restsecure.request.specification.RequestSpecification;
import com.restsecure.response.Response;

/**
 * Allows you to handle response and get some information about response or change response
 */
public interface ResponseHandler {
    void handleResponse(Response response, RequestSpecification spec);
}
