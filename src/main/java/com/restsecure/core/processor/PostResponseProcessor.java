package com.restsecure.core.processor;

import com.restsecure.core.request.RequestContext;

public interface PostResponseProcessor {
    void postResponseProcess(RequestContext context);
}
