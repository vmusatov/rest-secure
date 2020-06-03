package com.restsecure.http;

import com.restsecure.request.specification.RequestSpecification;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.restsecure.http.RequestMethod.DELETE;
import static com.restsecure.http.RequestMethod.GET;

public class HttpHelper {

    public static List<NameValuePair> getFilteredParameters(List<Parameter> parameters) {
        return parameters.stream()
                .filter(Objects::nonNull)
                .filter(parameter -> parameter.getValue() != null)
                .filter(parameter -> !parameter.getValue().equals("null"))
                .map(parameter -> new BasicNameValuePair(parameter.getName(), parameter.getValue()))
                .collect(Collectors.toList());
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

            if (!isHaveBody(method)) {
                uriBuilder.setParameters(getFilteredParameters(specification.getParameters()));
            }

            if (specification.getPort() != 0) {
                uriBuilder.setPort(specification.getPort());
            }

            return uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void setEntityToRequest(RequestSpecification specification, HttpEntityEnclosingRequestBase request) {
        List<NameValuePair> params = HttpHelper.getFilteredParameters(specification.getParameters());
        request.setEntity(
                new UrlEncodedFormEntity(params, StandardCharsets.UTF_8)
        );
    }

    public static void setHeadersToRequest(List<Header> headers, HttpUriRequest request) {
        List<org.apache.http.Header> apacheHeadersList = headers
                .stream()
                .map(restSecureHeader -> new BasicHeader(restSecureHeader.getName(), restSecureHeader.getValue()))
                .collect(Collectors.toList());

        request.setHeaders(apacheHeadersList.toArray(new org.apache.http.Header[headers.size()]));
    }

    public static List<Cookie> getCookiesFromHeaders(List<Header> headers) {
        MultiValueList<Header> headersWithCookies = new MultiValueList<>(headers).getAll("Set-Cookie");

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
