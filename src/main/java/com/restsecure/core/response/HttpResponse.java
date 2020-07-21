package com.restsecure.core.response;

import com.restsecure.core.http.cookie.Cookie;
import com.restsecure.core.http.header.Header;
import com.restsecure.core.util.NameValueList;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HttpResponse implements Response {
    @Getter
    @Setter
    private ResponseBody body;
    @Getter
    @Setter
    private int statusCode;
    @Getter
    @Setter
    private String statusLine;
    private NameValueList<Header> headers;
    private NameValueList<Cookie> cookies;
    @Getter
    @Setter
    private long time;

    public HttpResponse() {
        this.headers = new NameValueList<>();
        this.cookies = new NameValueList<>();
    }

    @Override
    public NameValueList<Header> getHeaders() {
        return headers;
    }

    @Override
    public void setHeaders(List<Header> headers) {
        this.headers = new NameValueList<>(headers);
    }

    @Override
    public NameValueList<Cookie> getCookies() {
        return this.cookies;
    }

    @Override
    public void setCookies(List<Cookie> cookies) {
        this.cookies = new NameValueList<>(cookies);
    }

    @Override
    public long getTimeIn(TimeUnit timeUnit) {
        return timeUnit.convert(this.time, TimeUnit.MILLISECONDS);
    }
}
