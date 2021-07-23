package com.restsecure.core.request.specification;

class SpecificationMerger {

    static void merge(MutableRequestSpec from, RequestSpec to) {
        to.url(from.getUrl());
        to.method(from.getMethod());
        to.port(from.getPort());

        to.body(from.getBody());

        from.getParams().forEach(param -> to.param(param.getKey(), param.getValue()));
        from.getRouteParams().forEach(param -> to.routeParam(param.getKey(), param.getValue()));
        from.getQueryParams().forEach(param -> to.queryParam(param.getKey(), param.getValue()));

        from.getHeaders().forEach(header -> to.header(header.getKey(), header.getValue()));
        from.getCookiesWithValueToSerialize().forEach(cookie -> to.cookie(cookie.getKey(), cookie.getValue()));

        to.process(from.getProcessors());
        to.expect(from.getValidations());
        to.config(from.getConfigs());
    }
}
