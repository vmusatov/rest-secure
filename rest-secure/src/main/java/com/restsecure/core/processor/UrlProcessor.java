package com.restsecure.core.processor;

import com.restsecure.core.configuration.configs.BaseUrlConfig;
import com.restsecure.core.exception.RestSecureException;
import com.restsecure.core.http.HttpHelper;
import com.restsecure.core.http.RequestMethod;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.specification.RequestSpec;
import com.restsecure.core.util.MultiKeyMap;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.restsecure.core.http.HttpHelper.toApacheNameValuePair;

@ProcessAll
public class UrlProcessor implements Processor {

    @Override
    public void processRequest(RequestContext context) {
        setBaseUrl(context);
        applyRouteParams(context);
        buildUrl(context);
    }

    private void setBaseUrl(RequestContext context) {
        context.getConfigValue(BaseUrlConfig.class).ifPresent(baseUrl -> {
            String url = context.getRequestSpec().getUrl();

            if (!baseUrl.isEmpty() && !url.startsWith("http")) {
                context.getRequestSpec().url(baseUrl + url);
            }
        });
    }

    private void applyRouteParams(RequestContext context) {
        String url = context.getRequestSpec().getUrl();
        MultiKeyMap<String, Object> routeParams = context.getRequestSpec().getRouteParams();

        for (MultiKeyMap.Entity<String, Object> param : routeParams) {
            if (!url.contains("{" + param.getKey() + "}")) {
                throw new RuntimeException("Can't find route parameter name \"" + param.getKey() + "\"");
            }
            String value = URLEncoder.encode(String.valueOf(param.getValue()), StandardCharsets.UTF_8);
            url = url.replaceAll("\\{" + param.getKey() + "\\}", value);
        }

        context.getRequestSpec().url(url);
    }

    public static void buildUrl(RequestContext context) {
        try {
            RequestSpec spec = context.getRequestSpec();
            URIBuilder uriBuilder = new URIBuilder(spec.getUrl());
            RequestMethod method = spec.getMethod();

            if (method == null) {
                throw new RestSecureException("Http method is null");
            }

            uriBuilder.addParameters(toApacheNameValuePair(spec.getQueryParams()));

            if (!HttpHelper.isHaveBody(method)) {
                uriBuilder.addParameters(toApacheNameValuePair(spec.getParameters()));
            }

            if (spec.getPort() != 0) {
                uriBuilder.setPort(spec.getPort());
            }

            spec.url(uriBuilder.build().toString());
        } catch (URISyntaxException e) {
            throw new RestSecureException(e);
        }
    }

    @Override
    public int getRequestProcessOrder() {
        return Processor.MAX_ORDER - 50;
    }
}
