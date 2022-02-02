package com.restsecure.core.processor;

import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.MutableResponse;
import com.restsecure.core.response.Response;

public interface Processor {
    int DEFAULT_ORDER = 1000;
    int MAX_ORDER = Integer.MAX_VALUE;
    int MIN_ORDER = Integer.MIN_VALUE;

    /**
     * Override this method if you need process request
     *
     * @param context request context
     */
    default void processRequest(RequestContext context) {
    }

    /**
     * Override this method if you need process response
     *
     * @param context  request context
     * @param response response
     */
    default void processResponse(RequestContext context, MutableResponse response) {
    }

    /**
     * Override this method if you need set custom request process order
     *
     * @return request process order
     */
    default int getRequestProcessOrder() {
        return DEFAULT_ORDER;
    }

    /**
     * Override this method if you need set custom response process order
     *
     * @return response process order
     */
    default int getResponseProcessOrder() {
        return DEFAULT_ORDER;
    }
}
