package com.restsecure.core.request.specification;

import com.restsecure.core.http.Proxy.Proxy;

public class SpecificationMerger {

    public static void merge(RequestSpecification from, RequestSpecification to) {
        to.url(from.getUrl());
        to.method(from.getMethod());
        to.port(from.getPort());

        to.data(from.getData());

        from.getParameters().forEach(param -> to.param(param.getKey(), param.getValue()));
        from.getRouteParams().forEach(param -> to.routeParam(param.getKey(), param.getValue()));
        from.getHeaders().forEach(header -> to.header(header.getKey(), header.getValue()));
        from.getCookiesWithValueToSerialize().forEach(cookie -> to.cookie(cookie.getKey(), cookie.getValue()));

        mergeProxy(from, to);

        to.process(from.getProcessors());
        to.validate(from.getValidations());
        to.config(from.getConfigs());
    }

    private static void mergeProxy(RequestSpecification from, RequestSpecification to) {
        Proxy proxy = from.getProxy();
        if (proxy == null) {
            return;
        }
        to.proxy(proxy.getHost(), proxy.getPort(), proxy.getUsername(), proxy.getPassword());
    }
}
