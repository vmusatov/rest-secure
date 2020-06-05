package com.restsecure.request.specification;

public class SpecificationMerger {

    public static void merge(RequestSpecification from, RequestSpecification to) {

        to.url(from.getUrl());
        to.method(from.getMethod());
        to.port(from.getPort());

        to.data(from.getData());

        to.params(from.getParameters());
        to.headers(from.getHeaders());

        to.handleRequest(from.getRequestHandlers());
        to.handleResponse(from.getResponseHandlers());

        to.validate(from.getValidations());

        to.config(from.getConfigs());
    }
}
