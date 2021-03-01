package com.restsecure.core.http;

import com.restsecure.core.exception.RequestConfigurationException;
import com.restsecure.core.exception.RestSecureException;
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

import static com.restsecure.core.http.RequestMethod.*;

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
        if (method.equals(GET) || method.equals(DELETE) || method.equals(HEAD) || method.equals(TRACE) || method.equals(OPTIONS)) {
            return false;
        }
        return true;
    }

    public static URI toURI(String str) {
        try {
            return new URIBuilder(str).build();
        } catch (URISyntaxException e) {
            throw new RestSecureException(e);
        }
    }

    public static void setEntityToRequest(RequestSpecification specification, HttpEntityEnclosingRequestBase request) {
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
            throw new RestSecureException(e.getMessage());
        }
    }
}
