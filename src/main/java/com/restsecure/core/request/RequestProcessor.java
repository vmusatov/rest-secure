package com.restsecure.core.request;

import com.restsecure.RestSecure;
import com.restsecure.core.processor.PreSendProcessor;

public class RequestProcessor {

    public static void process(RequestContext context) {
        context.getSpecification().processRequest(RestSecure.getGlobalPreSendProcessors());

        for (PreSendProcessor handler : context.getSpecification().getPreSendProcessors()) {
            handler.preSendProcess(context);
        }
    }
}
