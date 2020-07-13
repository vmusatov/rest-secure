package com.restsecure.core.response;

import com.restsecure.core.http.Cookie;
import com.restsecure.core.http.Header;
import com.restsecure.core.util.NameValueList;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class HttpResponse implements Response {
    @Getter
    @Setter
    private ResponseBody body;
    @Getter
    @Setter
    private int statusCode;
    private NameValueList<Header> headers;
    private NameValueList<Cookie> cookies;

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
}
