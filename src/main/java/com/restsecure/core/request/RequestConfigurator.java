package com.restsecure.core.request;

import com.restsecure.RestSecure;
import com.restsecure.core.http.HttpHelper;
import com.restsecure.core.processor.PreSendProcessor;
import com.restsecure.core.processor.ProcessScope;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.request.specification.RequestSpecificationImpl;

import static com.restsecure.core.processor.ProcessScope.*;

public class RequestConfigurator {

    public static void configure(RequestContext context) {
        mergeWithGlobalSpec(context);
        processRequest(context);
        setBaseUrlIfNeed(context);
    }

    private static void mergeWithGlobalSpec(RequestContext context) {
        RequestSpecification specification = new RequestSpecificationImpl()
                .mergeWith(RestSecure.globalSpecification)
                .mergeWith(context.getSpecification());

        context.setSpecification(specification);
    }

    private static void setBaseUrlIfNeed(RequestContext context) {
        String domainName = HttpHelper.getDomainName(context.getSpecification().getUrl());
        if (domainName == null || domainName.isEmpty()) {
            String url = RestSecure.baseUrl + context.getSpecification().getUrl();
            context.getSpecification().url(url);
        }
    }

    private static void processRequest(RequestContext context) {
        callPreSendProcessors(BEFORE_ALL, context);
        callPreSendProcessors(BEFORE_SPECIFIED, context);

        for (PreSendProcessor handler : context.getSpecification().getPreSendProcessors()) {
            handler.preSendProcess(context);
        }

        callPreSendProcessors(AFTER_SPECIFIED, context);
        callPreSendProcessors(AFTER_ALL, context);
    }

    private static void callPreSendProcessors(ProcessScope scope, RequestContext context) {
        RestSecure.getContext().getPreSendProcessors().get(scope).forEach(
                preSendProcessor -> preSendProcessor.preSendProcess(context)
        );
    }
}
