package com.restsecure.core.http;

import com.restsecure.RestSecure;
import com.restsecure.core.exception.RestSecureException;
import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.util.MultiKeyMap;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.restsecure.core.http.HttpHelper.getFilteredParameters;
import static com.restsecure.core.http.HttpHelper.isHaveBody;

@ProcessAll
public class UrlProcessor implements Processor {

    @Override
    public void processRequest(RequestContext context) {
        applyRouteParams(context);
        setBaseUrl(context);
        buildUrl(context);
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

    public static void buildUrl(RequestContext context) {
        try {
            RequestSpecification specification = context.getSpecification();
            URIBuilder uriBuilder = new URIBuilder(specification.getUrl());
            RequestMethod method = specification.getMethod();

            if (method == null) {
                throw new RestSecureException("Http method is null");
            }

            uriBuilder.addParameters(getFilteredParameters(specification.getQueryParams()));

            if (!isHaveBody(method)) {
                uriBuilder.addParameters(getFilteredParameters(specification.getParameters()));
            }

            if (specification.getPort() != 0) {
                uriBuilder.setPort(specification.getPort());
            }

            specification.url(uriBuilder.build().toString());
        } catch (URISyntaxException e) {
            throw new RestSecureException(e);
        }
    }

    @Override
    public int getRequestProcessOrder() {
        return Processor.MAX_ORDER - 50;
    }
}
