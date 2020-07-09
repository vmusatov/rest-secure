package com.restsecure.core.request;

import com.restsecure.RestSecure;
import com.restsecure.core.http.HttpHelper;
import com.restsecure.core.request.data.RequestDataConfigurator;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.request.specification.RequestSpecificationImpl;

public class RequestConfigurator {

    public static void configure(RequestContext context) {

        RequestSpecification specification = new RequestSpecificationImpl()
                .mergeWith(RestSecure.globalSpecification)
                .mergeWith(context.getSpecification());

        context.setSpecification(specification);

        RequestDataConfigurator.configure(context);

        String domainName = HttpHelper.getDomainName(context.getSpecification().getUrl());
        if (domainName == null || domainName.isEmpty()) {
            String url = RestSecure.baseUrl + context.getSpecification().getUrl();
            context.getSpecification().url(url);
        }
    }
}
