package com.restsecure.authentication;

import com.restsecure.core.processor.PreSendProcessor;
import com.restsecure.core.request.RequestContext;

public class BearerTokenAuthentication implements PreSendProcessor {

    private final String token;

    public BearerTokenAuthentication(String token) {
        this.token = token;
    }

    @Override
    public void preSendProcess(RequestContext context) {
        context.getSpecification().header("Authorization", "Bearer " + this.token);
    }
}
