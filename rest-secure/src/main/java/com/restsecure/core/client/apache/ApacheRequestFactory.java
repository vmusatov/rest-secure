package com.restsecure.core.client.apache;

import com.restsecure.core.configuration.configs.*;
import com.restsecure.core.exception.RequestConfigurationException;
import com.restsecure.core.exception.RestSecureException;
import com.restsecure.core.http.HttpHelper;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.RequestFactory;
import com.restsecure.core.request.specification.MutableRequestSpec;
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

public class ApacheRequestFactory implements RequestFactory<HttpUriRequest> {

    @Override
    public HttpUriRequest createRequest(RequestContext context) {
        HttpRequestBase request = createApacheRequest(context.getRequestSpec());

        RequestConfig requestConfig = createRequestConfig(context);
        request.setConfig(requestConfig);

        return request;
    }

    private RequestConfig createRequestConfig(RequestContext context) {
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

    private HttpRequestBase createApacheRequest(MutableRequestSpec spec) {
        URI uri = toURI(spec.getUrl());
        HttpRequestBase request;

        switch (spec.getMethod()) {
            case GET:
                request = new HttpGet(uri);
                break;
            case DELETE:
                request = new HttpDelete(uri);
                break;
            case PUT:
                request = entityRequest(spec, new HttpPut(uri));
                break;
            case POST:
                request = entityRequest(spec, new HttpPost(uri));
                break;
            case HEAD:
                request = new HttpHead(uri);
                break;
            case TRACE:
                request = new HttpTrace(uri);
                break;
            case OPTIONS:
                request = new HttpOptions(uri);
                break;
            case PATCH:
                request = entityRequest(spec, new HttpPatch(uri));
                break;
            default:
                throw new RequestConfigurationException("Unsupported request method " + spec.getMethod());
        }

        setHeadersToRequest(spec.getHeaders(), request);

        return request;
    }

    private URI toURI(String str) {
        try {
            return new URIBuilder(str).build();
        } catch (URISyntaxException e) {
            throw new RestSecureException(e);
        }
    }

    private void setHeadersToRequest(MultiKeyMap<String, Object> headers, HttpUriRequest request) {
        headers.forEach(header -> {
            BasicHeader basicHeader = new BasicHeader(header.getKey(), header.getValue().toString());
            request.addHeader(basicHeader);
        });
    }

    private HttpEntityEnclosingRequestBase entityRequest(MutableRequestSpec spec, HttpEntityEnclosingRequestBase request) {
        List<NameValuePair> params = HttpHelper.toApacheNameValuePair(spec.getParams());
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

        return request;
    }
}
