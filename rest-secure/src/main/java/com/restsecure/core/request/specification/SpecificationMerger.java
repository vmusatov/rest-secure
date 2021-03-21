package com.restsecure.core.request.specification;

import com.restsecure.core.http.Proxy;

public class SpecificationMerger {

    public static void merge(RequestSpec from, RequestSpec to) {
        to.url(from.getUrl());
        to.method(from.getMethod());
        to.port(from.getPort());

        to.body(from.getBody());

        from.getParameters().forEach(param -> to.param(param.getKey(), param.getValue()));
        from.getRouteParams().forEach(param -> to.routeParam(param.getKey(), param.getValue()));
        from.getQueryParams().forEach(param -> to.queryParam(param.getKey(), param.getValue()));

        from.getHeaders().forEach(header -> to.header(header.getKey(), header.getValue()));
        from.getCookiesWithValueToSerialize().forEach(cookie -> to.cookie(cookie.getKey(), cookie.getValue()));

        mergeProxy(from, to);

        to.process(from.getProcessors());
        to.expect(from.getValidations());
        to.config(from.getConfigs());
    }

    private static void mergeProxy(RequestSpec from, RequestSpec to) {
        Proxy proxy = from.getProxy();
        if (proxy == null) {
            to.proxy(null);
            return;
        }
        to.proxy(proxy.getHost(), proxy.getPort(), proxy.getUsername(), proxy.getPassword());
    }
}
