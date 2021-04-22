package com.restsecure.core.client;

import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.Response;

public interface Client {
    /**
     * Allows you to send request by specified RequestContext
     *
     * @param context RequestContext
     * @return Response
     */
    Response send(RequestContext context);

    /**
     * Close http client
     */
    void close();
}
