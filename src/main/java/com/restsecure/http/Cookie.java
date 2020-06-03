package com.restsecure.http;

import lombok.Data;

import java.net.HttpCookie;

@Data
public class Cookie implements NameAndValue {
    private String name;
    private String value;
    private String comment;
    private String commentURL;
    private boolean toDiscard;
    private String domain;
    private long maxAge = -1;
    private String path;
    private String portlist;
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
        this.commentURL = cookie.getCommentURL();
        this.toDiscard = cookie.getDiscard();
        this.domain = cookie.getDomain();
        this.maxAge = cookie.getMaxAge();
        this.path = cookie.getPath();
        this.portlist = cookie.getPortlist();
        this.secure = cookie.getSecure();
        this.httpOnly = cookie.isHttpOnly();
        this.version = cookie.getVersion();
    }
}