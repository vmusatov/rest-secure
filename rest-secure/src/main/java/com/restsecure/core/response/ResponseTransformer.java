package com.restsecure.core.response;

import com.restsecure.core.request.RequestContext;

public interface ResponseTransformer<T> {
    Response transform(T from, RequestContext context);
}
