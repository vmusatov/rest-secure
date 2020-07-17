package com.restsecure.core.request;

import com.restsecure.RestSecure;
import com.restsecure.core.http.HttpHelper;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.request.specification.RequestSpecificationImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        List<Processor> processors = new ArrayList<>();
        processors.addAll(RestSecure.getContext().getProcessors());
        processors.addAll(context.getSpecification().getProcessors());

        processors.stream()
                .sorted(Comparator.comparingInt(Processor::getRequestProcessOrder))
                .forEach(processor -> processor.processRequest(context));
    }
}
