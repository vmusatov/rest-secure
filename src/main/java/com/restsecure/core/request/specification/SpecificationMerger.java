package com.restsecure.core.request.specification;

public class SpecificationMerger {

    public static void merge(RequestSpecification from, RequestSpecification to) {

        to.url(from.getUrl());
        to.method(from.getMethod());
        to.port(from.getPort());

        to.data(from.getData());

        from.getParameters().forEach(param -> to.param(param.getKey(), param.getValue()));
        from.getHeaders().forEach(header -> to.header(header.getKey(), header.getValue()));

        to.processRequest(from.getPreSendProcessors());
        to.processResponse(from.getPostResponseProcessors());

        to.validate(from.getValidations());

        to.config(from.getConfigs());
    }
}
