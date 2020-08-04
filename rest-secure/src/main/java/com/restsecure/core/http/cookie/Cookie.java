package com.restsecure.core.http.cookie;

import com.restsecure.core.http.NameAndValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.net.HttpCookie;

@Data
@Builder
@AllArgsConstructor
public class Cookie implements NameAndValue {
    public static final String COMMENT = "Comment";
    public static final String DOMAIN = "Domain";
    public static final String MAX_AGE = "Max-Age";
    public static final String PATH = "Path";
    public static final String SECURE = "Secure";
    public static final String HTTP_ONLY = "HttpOnly";
    public static final String VERSION = "Version";

    private String name;
    private String value;
    private String comment;
    private String domain;
    private long maxAge = -1;
    private String path;
    private boolean secure;
    private boolean httpOnly;
    private int version = 1;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Cookie(String header) {
        HttpCookie cookie = HttpCookie.parse(header).get(0);

        this.name = cookie.getName();
        this.value = cookie.getValue();
        this.comment = cookie.getComment();
        this.domain = cookie.getDomain();
        this.maxAge = cookie.getMaxAge();
        this.path = cookie.getPath();
        this.secure = cookie.getSecure();
        this.httpOnly = cookie.isHttpOnly();
        this.version = cookie.getVersion();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(name);
        if (value != null) {
            builder.append("=").append(value);
        }
        if (comment != null) {
            builder.append(";").append(COMMENT).append("=").append(comment);
        }
        if (path != null) {
            builder.append(";").append(PATH).append("=").append(path);
        }
        if (domain != null) {
            builder.append(";").append(DOMAIN).append("=").append(domain);
        }
        if (maxAge != -1) {
            builder.append(";").append(MAX_AGE).append("=").append(maxAge);
        }
        if (secure) {
            builder.append(";").append(SECURE);
        }
        if (httpOnly) {
            builder.append(";").append(HTTP_ONLY);
        }
        if (version != -1) {
            builder.append(";").append(VERSION).append("=").append(version);
        }
        return builder.toString();
    }
}