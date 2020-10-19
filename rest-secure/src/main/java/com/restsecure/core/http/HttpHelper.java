package com.restsecure.core.http;

import com.restsecure.core.http.cookie.Cookie;
import com.restsecure.core.http.header.Header;
import com.restsecure.core.mapping.serialize.SerializeConfig;
import com.restsecure.core.exception.RequestConfigurationException;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.util.MultiKeyMap;
import com.restsecure.core.util.NameValueList;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.restsecure.core.http.RequestMethod.DELETE;
import static com.restsecure.core.http.RequestMethod.GET;

public class HttpHelper {

    public static List<NameValuePair> getFilteredParameters(MultiKeyMap<String, Object> parameters) {
        List<NameValuePair> pairs = new ArrayList<>();
        parameters.forEach(param -> {
            if (param.getValue() == null) {
                param.setValue("");
            }
            pairs.add(new BasicNameValuePair(param.getKey(), param.getValue().toString()));
        });

        return pairs;
    }

    public static boolean isHaveBody(RequestMethod method) {
        if (method.equals(GET) || method.equals(DELETE)) {
            return false;
        }
        return true;
    }

    public static URI buildUri(RequestSpecification specification) {
        try {
            URIBuilder uriBuilder = new URIBuilder(specification.getUrl());
            RequestMethod method = specification.getMethod();

            uriBuilder.addParameters(getFilteredParameters(specification.getQueryParams()));

            if (!isHaveBody(method)) {
                uriBuilder.addParameters(getFilteredParameters(specification.getParameters()));
            }

            if (specification.getPort() != 0) {
                uriBuilder.setPort(specification.getPort());
            }

            return uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void setEntityToRequest(RequestSpecification specification, SerializeConfig serializeConfig, HttpEntityEnclosingRequestBase request) {
        List<NameValuePair> params = HttpHelper.getFilteredParameters(specification.getParameters());
        Object body = specification.getBody();

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

    public static void setHeadersToRequest(MultiKeyMap<String, Object> headers, HttpUriRequest request) {
        headers.forEach(header -> {
            BasicHeader basicHeader = new BasicHeader(header.getKey(), header.getValue().toString());
            request.addHeader(basicHeader);
        });
    }

    public static List<Cookie> getCookiesFromHeaders(List<Header> headers) {
        NameValueList<Header> headersWithCookies = new NameValueList<>(headers).getAll("Set-Cookie");

        List<Cookie> cookies = new ArrayList<>();
        for (Header header : headersWithCookies) {
            cookies.add(new Cookie(header.getValue()));
        }
        return cookies;
    }

    public static String getDomainName(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            if (domain == null) {
                return null;
            }
            return domain.startsWith("www.") ? domain.substring(4) : domain;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
