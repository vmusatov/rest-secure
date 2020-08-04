package com.restsecure.core.http;

import com.restsecure.RestSecure;
import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.util.MultiKeyMap;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@ProcessAll
public class UrlProcessor implements Processor {

    @Override
    public void processRequest(RequestContext context) {
        applyRouteParams(context);
        setBaseUrl(context);
    }

    private void setBaseUrl(RequestContext context) {
        String domainName = HttpHelper.getDomainName(context.getSpecification().getUrl());
        if (domainName == null || domainName.isEmpty()) {
            String url = RestSecure.baseUrl + context.getSpecification().getUrl();
            context.getSpecification().url(url);
        }
    }

    private void applyRouteParams(RequestContext context) {
        String url = context.getSpecification().getUrl();
        MultiKeyMap<String, Object> routeParams = context.getSpecification().getRouteParams();

        for (MultiKeyMap.Entity<String, Object> param : routeParams) {
            if (!url.contains("{" + param.getKey() + "}")) {
                throw new RuntimeException("Can't find route parameter name \"" + param.getKey() + "\"");
            }
            String value = URLEncoder.encode(String.valueOf(param.getValue()), StandardCharsets.UTF_8);
            url = url.replaceAll("\\{" + param.getKey() + "\\}", value);
        }

        context.getSpecification().url(url);
    }

    @Override
    public int getRequestProcessOrder() {
        return Processor.MAX_ORDER - 50;
    }
}
