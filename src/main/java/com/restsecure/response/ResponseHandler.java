package com.restsecure.response;

import com.restsecure.request.specification.RequestSpecification;

/**
 * Allows you to handle response and get some information about response or change response
 */
public interface ResponseHandler {
    void handleResponse(Response response, RequestSpecification specification);
}
