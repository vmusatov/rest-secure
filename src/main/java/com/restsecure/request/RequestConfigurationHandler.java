package com.restsecure.request;

import com.restsecure.RestSecure;
import com.restsecure.http.HttpHelper;
import com.restsecure.request.specification.RequestSpecification;

public class RequestConfigurationHandler implements RequestHandler {

    @Override
    public void handleRequest(RequestSpecification spec) {
        String domainName = HttpHelper.getDomainName(spec.getUrl());
        if (domainName == null || domainName.isEmpty()) {
            String url = RestSecure.baseUrl + spec.getUrl();
            spec.url(url);
        }
    }
}
