package com.restsecure.core.client.apache;

import com.restsecure.core.configuration.configs.*;
import com.restsecure.core.exception.RequestConfigurationException;
import com.restsecure.core.exception.RestSecureException;
import com.restsecure.core.http.HttpHelper;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.specification.RequestSpec;
import com.restsecure.core.util.MultiKeyMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ApacheRequestFactory {

    public static HttpUriRequest createRequest(RequestContext context) {
        HttpRequestBase request = createApacheRequest(context.getRequestSpec());

        RequestConfig requestConfig = createRequestConfig(context);
        request.setConfig(requestConfig);

        return request;
    }

    private static RequestConfig createRequestConfig(RequestContext context) {
        RequestConfig.Builder builder = RequestConfig.custom();

        context.getConfigValue(ProxyConfig.class).ifPresent(proxy -> {
            builder.setProxy(new HttpHost(proxy.getName(), proxy.getPort()));
        });

        int connectionTimeout = context.getConfigValue(ConnectionTimeoutConfig.class);
        int socketTimeout = context.getConfigValue(SocketTimeoutConfig.class);
        boolean enableRedirects = context.getConfigValue(EnableRedirectsConfig.class);
        int maxRedirects = context.getConfigValue(MaxRedirectsConfig.class);
        String cookieSpec = context.getConfigValue(CookieSpecConfig.class);

        builder.setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(socketTimeout)
                .setConnectTimeout(connectionTimeout)
                .setRedirectsEnabled(enableRedirects)
                .setMaxRedirects(maxRedirects)
                .setCookieSpec(cookieSpec);

        return builder.build();
    }

    private static HttpRequestBase createApacheRequest(RequestSpec spec) {
        HttpRequestBase request;
        switch (spec.getMethod()) {
            case GET:
                request = createGet(spec);
                break;
            case DELETE:
                request = createDelete(spec);
                break;
            case PUT:
                request = createPut(spec);
                break;
            case POST:
                request = createPost(spec);
                break;
            case HEAD:
                request = createHead(spec);
                break;
            case TRACE:
                request = createTrace(spec);
                break;
            case OPTIONS:
                request = createOptions(spec);
                break;
            case PATCH:
                request = createPatch(spec);
                break;
            default:
                throw new RequestConfigurationException("Unsupported request method " + spec.getMethod());
        }
        return request;
    }

    private static HttpRequestBase createPost(RequestSpec spec) {
        URI uri = toURI(spec.getUrl());
        HttpPost request = new HttpPost(uri);
        setHeadersToRequest(spec.getHeaders(), request);
        setEntityToRequest(spec, request);

        return request;
    }

    private static HttpRequestBase createGet(RequestSpec spec) {
        URI uri = toURI(spec.getUrl());
        HttpGet request = new HttpGet(uri);
        setHeadersToRequest(spec.getHeaders(), request);

        return request;
    }

    private static HttpRequestBase createPut(RequestSpec spec) {
        URI uri = toURI(spec.getUrl());
        HttpPut request = new HttpPut(uri);
        setHeadersToRequest(spec.getHeaders(), request);
        setEntityToRequest(spec, request);

        return request;
    }

    private static HttpRequestBase createDelete(RequestSpec spec) {
        URI uri = toURI(spec.getUrl());
        HttpDelete request = new HttpDelete(uri);
        setHeadersToRequest(spec.getHeaders(), request);

        return request;
    }

    private static HttpRequestBase createHead(RequestSpec spec) {
        URI uri = toURI(spec.getUrl());
        HttpHead request = new HttpHead(uri);
        setHeadersToRequest(spec.getHeaders(), request);

        return request;
    }

    private static HttpRequestBase createTrace(RequestSpec spec) {
        URI uri = toURI(spec.getUrl());
        HttpTrace request = new HttpTrace(uri);
        setHeadersToRequest(spec.getHeaders(), request);

        return request;
    }

    private static HttpRequestBase createOptions(RequestSpec spec) {
        URI uri = toURI(spec.getUrl());
        HttpOptions request = new HttpOptions(uri);
        setHeadersToRequest(spec.getHeaders(), request);

        return request;
    }

    private static HttpRequestBase createPatch(RequestSpec spec) {
        URI uri = toURI(spec.getUrl());
        HttpPatch request = new HttpPatch(uri);
        setHeadersToRequest(spec.getHeaders(), request);
        setEntityToRequest(spec, request);

        return request;
    }

    private static URI toURI(String str) {
        try {
            return new URIBuilder(str).build();
        } catch (URISyntaxException e) {
            throw new RestSecureException(e);
        }
    }

    private static void setHeadersToRequest(MultiKeyMap<String, Object> headers, HttpUriRequest request) {
        headers.forEach(header -> {
            BasicHeader basicHeader = new BasicHeader(header.getKey(), header.getValue().toString());
            request.addHeader(basicHeader);
        });
    }

    private static void setEntityToRequest(RequestSpec spec, HttpEntityEnclosingRequestBase request) {
        List<NameValuePair> params = HttpHelper.toApacheNameValuePair(spec.getParameters());
        Object body = spec.getBody();

        if (!params.isEmpty() && body != null) {
            throw new RequestConfigurationException("You can specify either request body or form parameters");
        }

        HttpEntity entity = null;

        if (body != null) {
            entity = new StringEntity(body.toString(), StandardCharsets.UTF_8);
        }

        if (!params.isEmpty()) {
            entity = new UrlEncodedFormEntity(params, StandardCharsets.UTF_8);
        }

        if (entity != null) {
            request.setEntity(entity);
        }
    }
}
