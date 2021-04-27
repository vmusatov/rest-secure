package com.restsecure.core.request;

public interface RequestFactory<T> {
    T createRequest(RequestContext context);
}
