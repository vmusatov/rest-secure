package com.restsecure.core.request.specification;

import com.restsecure.core.http.proxy.Proxy;

public class SpecificationMerger {

    public static void merge(RequestSpecification from, RequestSpecification to) {
        if (from.getUrl() != null && !from.getUrl().isEmpty()) {
            to.url(from.getUrl());
        }

        if (from.getMethod() != null) {
            to.method(from.getMethod());
        }

        if (from.getPort() > 0) {
            to.port(from.getPort());
        }

        to.body(from.getBody());

        from.getParameters().forEach(param -> to.param(param.getKey(), param.getValue()));
        from.getRouteParams().forEach(param -> to.routeParam(param.getKey(), param.getValue()));
        from.getHeaders().forEach(header -> to.header(header.getKey(), header.getValue()));
        from.getCookiesWithValueToSerialize().forEach(cookie -> to.cookie(cookie.getKey(), cookie.getValue()));

        mergeProxy(from, to);

        to.process(from.getProcessors());
        to.expect(from.getValidations());
        to.config(from.getConfigs());
        to.data(from.getData());
    }

    private static void mergeProxy(RequestSpecification from, RequestSpecification to) {
        Proxy proxy = from.getProxy();
        if (proxy == null) {
            return;
        }
        to.proxy(proxy.getHost(), proxy.getPort(), proxy.getUsername(), proxy.getPassword());
    }
}
