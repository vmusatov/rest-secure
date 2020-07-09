package com.restsecure.core.processor;

import com.restsecure.core.request.RequestContext;

public interface PreSendProcessor {
    void preSendProcess(RequestContext context);
}
