package com.restsecure.core.http;

import com.restsecure.core.exception.RestSecureException;
import com.restsecure.core.util.MultiKeyMap;
import com.restsecure.core.util.NameValueList;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.restsecure.core.http.RequestMethod.*;

public class HttpHelper {

    public static List<NameValuePair> toApacheNameValuePair(MultiKeyMap<String, Object> parameters) {
        List<NameValuePair> pairs = new ArrayList<>();
        parameters.forEach(param -> pairs.add(new BasicNameValuePair(param.getKey(), param.getValue().toString())));
        return pairs;
    }

    public static boolean isHaveBody(RequestMethod method) {
        if (method.equals(GET) || method.equals(DELETE) || method.equals(HEAD) || method.equals(TRACE) || method.equals(OPTIONS)) {
            return false;
        }
        return true;
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
